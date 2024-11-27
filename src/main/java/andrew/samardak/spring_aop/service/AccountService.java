package andrew.samardak.spring_aop.service;

import andrew.samardak.spring_aop.entity.Account;
import andrew.samardak.spring_aop.entity.Transaction;
import andrew.samardak.spring_aop.utils.enums.AccountStatus;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService extends RelatedCRUDService<Account, Long> {

    void updateAccountBalance(Long accountId, BigDecimal balance);

    List<Transaction> getTransactionsByAccountId(Long accountId);

    boolean isAccountStatus(Long accountId, AccountStatus status);
}
