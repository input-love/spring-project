package andrew.samardak.spring_aop.controller;

import andrew.samardak.spring_aop.aspect.LogDataSourceError;
import andrew.samardak.spring_aop.dto.request.TransactionRequestDto;
import andrew.samardak.spring_aop.dto.response.TransactionResponseDto;
import andrew.samardak.spring_aop.facade.TransactionFacade;
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
@RequestMapping("v1/transactions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionController {

    TransactionFacade transactionFacade;

    @PostMapping
    public TransactionResponseDto create(
            @RequestBody TransactionRequestDto dto
    ) {
        return transactionFacade.create(dto);
    }

    @GetMapping("/{id}")
    public TransactionResponseDto read(
            @PathVariable Long id
    ) {
        return transactionFacade.read(id);
    }

    @PatchMapping("/{id}")
    public TransactionResponseDto update(
            @PathVariable Long id,
            @RequestBody TransactionRequestDto dto
    ) {
        return transactionFacade.update(dto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id
    ) {
        transactionFacade.delete(id);
    }
}
