package andrew.samardak.spring_aop.service.impl;

import andrew.samardak.spring_aop.dto.kafka.TransactionAcceptDto;
import andrew.samardak.spring_aop.entity.Account;
import andrew.samardak.spring_aop.entity.Transaction;
import andrew.samardak.spring_aop.kafka.producer.KafkaTransactionAcceptProducer;
import andrew.samardak.spring_aop.mappers.TransactionAcceptMapper;
import andrew.samardak.spring_aop.repository.TransactionRepository;
import andrew.samardak.spring_aop.service.AccountService;
import andrew.samardak.spring_aop.service.TransactionService;
import andrew.samardak.spring_aop.utils.constants.KafkaHeaderConstants;
import andrew.samardak.spring_aop.utils.enums.AccountStatus;
import andrew.samardak.spring_aop.utils.enums.TransactionStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionServiceImpl implements TransactionService {

    AccountService accountService;
    TransactionRepository transactionRepository;
    TransactionAcceptMapper transactionAcceptMapper;

    KafkaTransactionAcceptProducer<TransactionAcceptDto> kafkaTransactionAcceptProducer;

    @Override
    public void processTransaction(Transaction entity, Long accountId) {
        if (accountService.checkAccountStatus(accountId, AccountStatus.OPEN)) {
            Transaction transaction = updateStatus(entity, TransactionStatus.REQUESTED);

            Account account = accountService.updateBalance(accountId, entity.getAmount());

            TransactionAcceptDto response = transactionAcceptMapper.toTransactionAcceptDto(account, transaction);

            kafkaTransactionAcceptProducer.sendMessage(response, buildHeader());
        }
    }

    @Override
    public Transaction updateStatus(Transaction entity, TransactionStatus status) {
        entity.setTransactionStatus(status);

        return transactionRepository.save(entity);
    }

    @Override
    public JpaRepository<Transaction, Long> getRepository() {
        return transactionRepository;
    }

    private Map<String, String> buildHeader() {
        return Map.of(
                KafkaHeaderConstants.ACCEPT_TYPE_HEADER, KafkaHeaderConstants.ERROR_TIMESTAMP_VALUE
        );
    }
}
