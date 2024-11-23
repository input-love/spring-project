package andrew.samardak.spring_aop.service;

import andrew.samardak.spring_aop.entity.Account;
import andrew.samardak.spring_aop.utils.enums.AccountStatus;

import java.math.BigDecimal;

public interface AccountService extends CRUDService<Account, Long> {

    Account updateBalance(Long id, BigDecimal amount);

    boolean checkAccountStatus(Long id, AccountStatus status);
}
