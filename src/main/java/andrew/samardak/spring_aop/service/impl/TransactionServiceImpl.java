package andrew.samardak.spring_aop.service.impl;

import andrew.samardak.spring_aop.dto.response.TransactionAcceptResponseDto;
import andrew.samardak.spring_aop.entity.Account;
import andrew.samardak.spring_aop.entity.Transaction;
import andrew.samardak.spring_aop.kafka.producer.KafkaProducerService;
import andrew.samardak.spring_aop.mappers.TransactionAcceptMapper;
import andrew.samardak.spring_aop.repository.TransactionRepository;
import andrew.samardak.spring_aop.service.AccountService;
import andrew.samardak.spring_aop.service.TransactionService;
import andrew.samardak.spring_aop.utils.constants.KafkaHeaderConstants;
import andrew.samardak.spring_aop.utils.enums.AccountStatus;
import andrew.samardak.spring_aop.utils.enums.TransactionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    @Value("${spring-project.kafka.topics.producer.transaction-accept}")
    private String topicTransactionAccept;

    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private final TransactionAcceptMapper transactionAcceptMapper;

    private final KafkaProducerService<TransactionAcceptResponseDto> kafkaProducerService;

    @Override
    public void processTransaction(Transaction entity, Long accountId) {
        if (accountService.checkAccountStatus(accountId, AccountStatus.OPEN)) {
            Transaction transaction = this.create(entity, accountId);

            this.updateStatus(transaction, TransactionStatus.REQUESTED);

            Account account = accountService.updateBalance(accountId, entity.getAmount());

            TransactionAcceptResponseDto response = transactionAcceptMapper.toTransactionAcceptDto(account, transaction);

            kafkaProducerService.sendMessage(topicTransactionAccept, response, buildHeader());
        }
    }

    @Override
    public void handleTransaction(Long transactionId, Long accountId, TransactionStatus status) {
        switch (status) {
            case ACCEPTED -> {
                Transaction entity = transactionRepository.findById(transactionId).orElseThrow();
                updateStatus(entity, TransactionStatus.ACCEPTED);
            }
            case BLOCKED -> {
                List<Transaction> transactions = accountService.getTransactionsByAccountId(accountId);

                BigDecimal sum = BigDecimal.ZERO;
                for (Transaction transaction : transactions) {
                    updateStatus(transaction, TransactionStatus.BLOCKED);
                    sum = sum.add(transaction.getAmount());
                }

                Account account = accountService.read(accountId);
                account.setAccountStatus(AccountStatus.BLOCKED);
                account.setFrozenAmount(sum);
                accountService.update(account);
            }
            case REJECTED -> {
                Transaction entity = transactionRepository.findById(transactionId).orElseThrow();
                updateStatus(entity, TransactionStatus.REJECTED);

                accountService.updateBalance(accountId, entity.getAmount());
            }
        }
    }

    @Override
    public Transaction updateStatus(Transaction entity, TransactionStatus status) {
        entity.setTransactionStatus(status);

        return transactionRepository.save(entity);
    }

    @Override
    public Transaction create(Transaction transaction, Long accountId) {
        Account account = accountService.read(accountId);

        transaction.setAccount(account);

        return transactionRepository.save(transaction);
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
