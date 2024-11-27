package andrew.samardak.spring_aop.service.impl;

import andrew.samardak.spring_aop.entity.Account;
import andrew.samardak.spring_aop.entity.Client;
import andrew.samardak.spring_aop.entity.Transaction;
import andrew.samardak.spring_aop.repository.AccountRepository;
import andrew.samardak.spring_aop.service.AccountService;
import andrew.samardak.spring_aop.service.ClientService;
import andrew.samardak.spring_aop.utils.enums.AccountStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final ClientService clientService;
    private final AccountRepository accountRepository;

    @Override
    public Account updateBalance(Long id, BigDecimal amount) {
        Account account = accountRepository.findById(id).orElseThrow();

        BigDecimal updatedBalance = account.getBalance().subtract(amount);
        account.setBalance(updatedBalance);

        return accountRepository.save(account);
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(Long id) {
        Account account = accountRepository.findById(id).orElseThrow();

        return account.getTransactions();
    }

    @Override
    public boolean checkAccountStatus(Long id, AccountStatus status) {
        Account account = accountRepository.findById(id).orElseThrow();

        return account.getAccountStatus().equals(status);
    }

    @Override
    public Account create(Account account, Long clientId) {
        Client client = clientService.read(clientId);

        account.setClient(client);

        return accountRepository.save(account);
    }

    @Override
    public JpaRepository<Account, Long> getRepository() {
        return accountRepository;
    }
}
