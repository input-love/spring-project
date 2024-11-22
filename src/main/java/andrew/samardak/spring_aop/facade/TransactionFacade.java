package andrew.samardak.spring_aop.facade;

import andrew.samardak.spring_aop.dto.request.TransactionRequestDto;
import andrew.samardak.spring_aop.dto.response.TransactionResponseDto;
import andrew.samardak.spring_aop.entity.Transaction;
import andrew.samardak.spring_aop.mappers.TransactionMapper;
import andrew.samardak.spring_aop.service.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionFacade {

    TransactionService transactionService;
    TransactionMapper transactionMapper;

    public TransactionResponseDto create(TransactionRequestDto dto) {
        Transaction account = transactionService.create(dto);

        return transactionMapper.toDto(account);
    }

    public TransactionResponseDto read(Long id) {
        Transaction account = transactionService.read(id);

        return transactionMapper.toDto(account);
    }

    public TransactionResponseDto update(TransactionRequestDto dto, Long id) {
        Transaction update = transactionService.update(dto, id);

        return transactionMapper.toDto(update);
    }

    public void delete(Long id) {
        transactionService.delete(id);
    }
}
