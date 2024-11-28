package andrew.samardak.spring_aop.service.impl;

import andrew.samardak.spring_aop.entity.Account;
import andrew.samardak.spring_aop.entity.Client;
import andrew.samardak.spring_aop.repository.AccountRepository;
import andrew.samardak.spring_aop.service.AccountService;
import andrew.samardak.spring_aop.service.ClientService;
import andrew.samardak.spring_aop.utils.enums.AccountStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final ClientService clientService;
    private final AccountRepository accountRepository;

    @Override
    public void updateAccountBalance(Long accountId, BigDecimal balance) {
        Account account = accountRepository.findById(accountId).orElseThrow();

        account.setBalance(balance);

        accountRepository.save(account);
    }

    @Override
    public Account createWithRelations(Account account, Long clientId) {
        Client client = clientService.read(clientId);

        account.setClient(client);

        return accountRepository.save(account);
    }

    @Override
    public JpaRepository<Account, Long> getRepository() {
        return accountRepository;
    }
}
