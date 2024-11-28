package andrew.samardak.spring_aop.service;

import andrew.samardak.spring_aop.entity.Account;

import java.math.BigDecimal;

public interface AccountService extends RelatedCRUDService<Account, Long> {

    void updateAccountBalance(Long accountId, BigDecimal balance);
}
