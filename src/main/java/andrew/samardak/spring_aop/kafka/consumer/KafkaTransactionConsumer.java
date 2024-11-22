package andrew.samardak.spring_aop.kafka.consumer;

import andrew.samardak.spring_aop.dto.kafka.TransactionAcceptDto;
import andrew.samardak.spring_aop.dto.request.TransactionRequestDto;
import andrew.samardak.spring_aop.entity.Account;
import andrew.samardak.spring_aop.entity.Transaction;
import andrew.samardak.spring_aop.kafka.producer.KafkaTransactionAcceptProducer;
import andrew.samardak.spring_aop.mappers.TransactionMapper;
import andrew.samardak.spring_aop.service.AccountService;
import andrew.samardak.spring_aop.service.TransactionService;
import andrew.samardak.spring_aop.utils.constants.KafkaHeaderConstants;
import andrew.samardak.spring_aop.utils.enums.AccountStatus;
import andrew.samardak.spring_aop.utils.enums.TransactionStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaTransactionConsumer {

    AccountService accountService;
    TransactionService transactionService;
    TransactionMapper transactionMapper;

    KafkaTransactionAcceptProducer<TransactionAcceptDto> kafkaTransactionAcceptProducer;

    @KafkaListener(
            id = "${spring-project.kafka.topic.transactions.id}",
            topics = "${spring-project.kafka.topic.transactions.name}",
            containerFactory = "kafkaTransactionListenerContainerFactory"
    )
    public void listener(
            @Payload TransactionRequestDto dto
    ) {
        Long accountId = dto.getAccountId();

        Account account = accountService.read(accountId);

        AccountStatus accountStatus = account.getAccountStatus();

        // Проверка статуса счета (если статус `OPEN`):
        if (accountStatus.equals(AccountStatus.OPEN)) {
            // Сохраняет транзакцию в БД со статусом `REQUESTED`.
            Transaction entity = transactionMapper.toEntity(dto);

            dto.setTransactionStatus(TransactionStatus.REQUESTED); // ????

            Transaction transaction = transactionService.create(entity);

            // Изменяет счет клиента на сумму транзакции.
            BigDecimal balance = account.getBalance();
            BigDecimal amount = transaction.getAmount();

            account.setBalance(balance.subtract(amount));

            accountService.update(account);

            // Отправляет сообщение в топик `t1_demo_transaction_accept`
            TransactionAcceptDto responseDto = new TransactionAcceptDto(
                    account.getClient().getId(),
                    account.getId(),
                    transaction.getId(),
                    LocalDateTime.now(),
                    new TransactionAcceptDto.TransactionDetails(amount),
                    new TransactionAcceptDto.AccountDetails(balance)
            );

            kafkaTransactionAcceptProducer.sendMessage(responseDto, buildHeader());
        }
    }

    // НЕ ТАКОЙ ХЕДЕР ДОЛЖЕН БЫТЬ - ТЕСТ
    private Map<String, String> buildHeader() {
        return Map.of(
                KafkaHeaderConstants.ERROR_TYPE_HEADER, KafkaHeaderConstants.ERROR_METRIC_VALUE
        );
    }
}
