package andrew.samardak.spring_aop.facade;

import andrew.samardak.spring_aop.dto.request.AccountRequestDto;
import andrew.samardak.spring_aop.dto.response.AccountResponseDto;
import andrew.samardak.spring_aop.entity.Account;
import andrew.samardak.spring_aop.mappers.AccountMapper;
import andrew.samardak.spring_aop.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountFacade {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    public AccountResponseDto create(AccountRequestDto dto) {
        Account entity = accountMapper.toEntity(dto);

        Account account = accountService.create(entity);

        return accountMapper.toDto(account);
    }

    public AccountResponseDto read(Long id) {
        Account account = accountService.read(id);

        return accountMapper.toDto(account);
    }

    public AccountResponseDto update(AccountRequestDto dto, Long id) {
        Account entity = accountMapper.toEntity(dto);

        accountMapper.updateEntity(entity, dto);

        Account account = accountService.update(entity);

        return accountMapper.toDto(account);
    }

    public void delete(Long id) {
        accountService.delete(id);
    }
}
