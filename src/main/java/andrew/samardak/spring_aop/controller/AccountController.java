package andrew.samardak.spring_aop.controller;

import andrew.samardak.spring_aop.aspect.LogDataSourceError;
import andrew.samardak.spring_aop.aspect.Metric;
import andrew.samardak.spring_aop.dto.request.AccountRequestDto;
import andrew.samardak.spring_aop.dto.response.AccountResponseDto;
import andrew.samardak.spring_aop.service.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@LogDataSourceError
@RequiredArgsConstructor
@RequestMapping("v1/accounts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {

    AccountService accountService;

    @PostMapping
    public AccountResponseDto create(
            @RequestBody AccountRequestDto dto
    ) {
        return accountService.create(dto);
    }

    @Metric(1)
    @GetMapping("/{id}")
    public AccountResponseDto read(
            @PathVariable Long id
    ) {
        return accountService.read(id);
    }

    @PatchMapping("/{id}")
    public AccountResponseDto update(
            @PathVariable Long id,
            @RequestBody AccountRequestDto dto
    ) {
        return accountService.update(dto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id
    ) {
        accountService.delete(id);
    }
}
