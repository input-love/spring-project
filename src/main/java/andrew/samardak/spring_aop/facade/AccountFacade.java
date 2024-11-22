package andrew.samardak.spring_aop.facade;

import andrew.samardak.spring_aop.dto.request.AccountRequestDto;
import andrew.samardak.spring_aop.dto.response.AccountResponseDto;
import andrew.samardak.spring_aop.entity.Account;
import andrew.samardak.spring_aop.mappers.AccountMapper;
import andrew.samardak.spring_aop.service.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountFacade {

    AccountService accountService;
    AccountMapper accountMapper;

    public AccountResponseDto create(AccountRequestDto dto) {
        Account account = accountService.create(dto);

        return accountMapper.toDto(account);
    }

    public AccountResponseDto read(Long id) {
        Account account = accountService.read(id);

        return accountMapper.toDto(account);
    }

    public AccountResponseDto update(AccountRequestDto dto, Long id) {
        Account update = accountService.update(dto, id);

        return accountMapper.toDto(update);
    }

    public void delete(Long id) {
        accountService.delete(id);
    }
}
