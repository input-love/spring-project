package andrew.samardak.spring_aop.kafka.consumer;

import andrew.samardak.spring_aop.dto.request.TransactionResultRequestDto;
import andrew.samardak.spring_aop.entity.Account;
import andrew.samardak.spring_aop.entity.Transaction;
import andrew.samardak.spring_aop.service.AccountService;
import andrew.samardak.spring_aop.service.TransactionService;
import andrew.samardak.spring_aop.utils.enums.AccountStatus;
import andrew.samardak.spring_aop.utils.enums.TransactionStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaTransactionResultConsumer {

    AccountService accountService;
    TransactionService transactionService;

    @KafkaListener(
            id = "${spring-project.kafka.topic.transactions-result.id}",
            topics = "${spring-project.kafka.topic.transactions-result.name}",
            containerFactory = "kafkaTransactionResultListenerContainerFactory"
    )
    public void listener(
            @Payload TransactionResultRequestDto dto
    ) {
        TransactionStatus status = dto.getTransactionStatus();
        switch (status) {
            case ACCEPTED -> {
                // 1. При получении сообщения со статусом ACCEPTED - обновляет статус транзакции в БД

                Long transactionId = dto.getTransactionId();
                Transaction transaction = transactionService.read(transactionId);
                transaction.setTransactionStatus(TransactionStatus.ACCEPTED);
                transactionService.update(transaction);
            }
            case BLOCKED -> {
                // 2. При получении BLOCKED - обновляет транзакциям статусы в БД и выставляет счёту статус BLOCKED.

                // Баланс счёта меняется следующим образом:
                // счет корректируется на сумму заблокированных транзакций, сумма записывается в поле frozenAmount

                Long accountId = dto.getAccountId();
                Account account = accountService.read(accountId);
                List<Transaction> transactions = account.getTransactions();
                BigDecimal sum = BigDecimal.ZERO;
                for (Transaction transaction : transactions) {
                    transaction.setTransactionStatus(TransactionStatus.BLOCKED);
                    sum = sum.add(transaction.getAmount());
                }
                transactionService.getRepository().saveAll(transactions);
                account.setAccountStatus(AccountStatus.BLOCKED);
                account.setFrozenAmount(sum);
                accountService.update(account);
            }
            case REJECTED -> {
                // 3. При получении REJECTED - обновляет статус транзакции, и на сумму транзакции изменяет баланс

                Long transactionId = dto.getTransactionId();
                Transaction transaction = transactionService.read(transactionId);
                transaction.setTransactionStatus(TransactionStatus.REJECTED);
                transactionService.update(transaction);

                Long accountId = dto.getAccountId();
                Account account = accountService.read(accountId);
                account.setBalance(account.getBalance().subtract(transaction.getAmount()));
                accountService.update(account);
            }
        }
    }
}
