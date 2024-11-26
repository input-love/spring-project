package andrew.samardak.spring_aop.controller;

import andrew.samardak.spring_aop.aspect.LogDataSourceError;
import andrew.samardak.spring_aop.aspect.Metric;
import andrew.samardak.spring_aop.dto.request.AccountRequestDto;
import andrew.samardak.spring_aop.dto.response.AccountResponseDto;
import andrew.samardak.spring_aop.facade.AccountFacade;
import lombok.RequiredArgsConstructor;
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
public class AccountController {

    private final AccountFacade accountFacade;

    @PostMapping
    public AccountResponseDto create(
            @RequestBody AccountRequestDto dto
    ) {
        return accountFacade.create(dto);
    }

    @Metric(1)
    @GetMapping("/{id}")
    public AccountResponseDto read(
            @PathVariable Long id
    ) {
        return accountFacade.read(id);
    }

    @PatchMapping("/{id}")
    public AccountResponseDto update(
            @PathVariable Long id,
            @RequestBody AccountRequestDto dto
    ) {
        return accountFacade.update(dto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id
    ) {
        accountFacade.delete(id);
    }
}
