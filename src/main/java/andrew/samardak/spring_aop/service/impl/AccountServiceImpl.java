package andrew.samardak.spring_aop.service.impl;

import andrew.samardak.spring_aop.entity.Account;
import andrew.samardak.spring_aop.repository.AccountRepository;
import andrew.samardak.spring_aop.service.AccountService;
import andrew.samardak.spring_aop.utils.enums.AccountStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;

    @Override
    public Account updateBalance(Long id, BigDecimal amount) {
        Account account = accountRepository.findById(id).orElseThrow();

        BigDecimal updatedBalance = account.getBalance().subtract(amount);
        account.setBalance(updatedBalance);

        return accountRepository.save(account);
    }

    @Override
    public boolean checkAccountStatus(Long id, AccountStatus status) {
        Account account = accountRepository.findById(id).orElseThrow();

        return account.getAccountStatus().equals(status);
    }

    @Override
    public JpaRepository<Account, Long> getRepository() {
        return accountRepository;
    }
}
