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
        Transaction entity = transactionMapper.toEntity(dto);

        Transaction transaction = transactionService.create(entity);

        return transactionMapper.toDto(transaction);
    }

    public TransactionResponseDto read(Long id) {
        Transaction transaction = transactionService.read(id);

        return transactionMapper.toDto(transaction);
    }

    public TransactionResponseDto update(TransactionRequestDto dto, Long id) {
        Transaction entity = transactionMapper.toEntity(dto);

        transactionMapper.updateEntity(entity, dto);

        Transaction transaction = transactionService.update(entity);

        return transactionMapper.toDto(transaction);
    }

    public void delete(Long id) {
        transactionService.delete(id);
    }
}
