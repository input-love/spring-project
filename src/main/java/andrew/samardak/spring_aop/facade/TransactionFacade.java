package andrew.samardak.spring_aop.facade;

import andrew.samardak.spring_aop.dto.request.TransactionRequestDto;
import andrew.samardak.spring_aop.dto.request.TransactionResultRequestDto;
import andrew.samardak.spring_aop.dto.response.TransactionResponseDto;
import andrew.samardak.spring_aop.entity.Transaction;
import andrew.samardak.spring_aop.mappers.TransactionMapper;
import andrew.samardak.spring_aop.service.TransactionService;
import andrew.samardak.spring_aop.utils.enums.TransactionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionFacade {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    public void processTransaction(TransactionRequestDto dto) {
        Long accountId = dto.getAccountId();

        Transaction entity = transactionMapper.toEntity(dto);

        transactionService.processTransaction(entity, accountId);
    }

    public void handleTransaction(TransactionResultRequestDto dto) {
        Long transactionId = dto.getTransactionId();
        Long accountId = dto.getAccountId();
        TransactionStatus status = dto.getTransactionStatus();

        transactionService.handleTransaction(transactionId, accountId, status);
    }

    public TransactionResponseDto create(TransactionRequestDto dto) {
        Transaction entity = transactionMapper.toEntity(dto);

        Transaction transaction = transactionService.create(entity, dto.getAccountId());

        return transactionMapper.toDto(transaction);
    }

    public TransactionResponseDto read(Long id) {
        Transaction transaction = transactionService.read(id);

        return transactionMapper.toDto(transaction);
    }

    public TransactionResponseDto update(TransactionRequestDto dto, Long id) {
        Transaction entity = transactionService.read(id);

        transactionMapper.updateEntity(entity, dto);

        Transaction transaction = transactionService.update(entity);

        return transactionMapper.toDto(transaction);
    }

    public void delete(Long id) {
        transactionService.delete(id);
    }
}
