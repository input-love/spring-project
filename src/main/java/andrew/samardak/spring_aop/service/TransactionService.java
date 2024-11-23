package andrew.samardak.spring_aop.service;

import andrew.samardak.spring_aop.entity.Transaction;
import andrew.samardak.spring_aop.utils.enums.TransactionStatus;

public interface TransactionService extends CRUDService<Transaction, Long> {

    void processTransaction(Transaction entity, Long accountId);

    Transaction updateStatus(Transaction entity, TransactionStatus status);
}
