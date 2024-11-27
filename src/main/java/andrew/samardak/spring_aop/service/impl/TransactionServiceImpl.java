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
    private String topic;

    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private final TransactionAcceptMapper transactionAcceptMapper;

    private final KafkaProducerService<TransactionAcceptResponseDto> kafkaProducerService;

    @Override
    public void processTransaction(Transaction entity, Long accountId) {
        if (accountService.isAccountStatus(accountId, AccountStatus.OPEN)) {
            Transaction transaction = this.createWithRelations(entity, accountId);
            this.updateTransactionStatus(transaction.getId(), TransactionStatus.REQUESTED);

            Account account = accountService.read(accountId);
            BigDecimal balance = account.getBalance().subtract(transaction.getAmount());
            accountService.updateAccountBalance(account.getId(), balance);

            TransactionAcceptResponseDto message = transactionAcceptMapper.toTransactionAcceptDto(account, transaction);

            kafkaProducerService.sendMessage(topic, message, buildHeader());
        }
    }

    @Override
    public void handleTransaction(Long transactionId, Long accountId, TransactionStatus status) {
        switch (status) {
            case ACCEPTED -> {
                Transaction transaction = transactionRepository.findById(transactionId).orElseThrow();
                updateTransactionStatus(transaction.getId(), TransactionStatus.ACCEPTED);
            }
            case BLOCKED -> {
                List<Transaction> transactions = accountService.getTransactionsByAccountId(accountId);

                BigDecimal sum = BigDecimal.ZERO;
                for (Transaction transaction : transactions) {
                    updateTransactionStatus(transaction.getId(), TransactionStatus.BLOCKED);
                    sum = sum.add(transaction.getAmount());
                }

                Account account = accountService.read(accountId);
                account.setAccountStatus(AccountStatus.BLOCKED);
                account.setFrozenAmount(sum);
                accountService.update(account);
            }
            case REJECTED -> {
                Transaction transaction = transactionRepository.findById(transactionId).orElseThrow();
                updateTransactionStatus(transaction.getId(), TransactionStatus.REJECTED);

                accountService.updateAccountBalance(accountId, transaction.getAmount());
            }
        }
    }

    @Override
    public void updateTransactionStatus(Long transactionId, TransactionStatus status) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow();

        transaction.setTransactionStatus(status);

        transactionRepository.save(transaction);
    }

    @Override
    public Transaction createWithRelations(Transaction transaction, Long accountId) {
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
