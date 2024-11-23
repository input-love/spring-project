package andrew.samardak.spring_aop.service;

import andrew.samardak.spring_aop.entity.Account;
import andrew.samardak.spring_aop.entity.Transaction;
import andrew.samardak.spring_aop.utils.enums.AccountStatus;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService extends CRUDService<Account, Long> {

    Account updateBalance(Long id, BigDecimal amount);

    List<Transaction> getTransactionsByAccountId(Long id);

    boolean checkAccountStatus(Long id, AccountStatus status);
}
