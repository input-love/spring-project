package andrew.samardak.spring_aop.service.impl;

import andrew.samardak.spring_aop.dto.request.TransactionRequestDto;
import andrew.samardak.spring_aop.dto.response.TransactionResponseDto;
import andrew.samardak.spring_aop.entity.Account;
import andrew.samardak.spring_aop.entity.Transaction;
import andrew.samardak.spring_aop.mappers.BaseMapper;
import andrew.samardak.spring_aop.mappers.TransactionMapper;
import andrew.samardak.spring_aop.repository.TransactionRepository;
import andrew.samardak.spring_aop.service.AccountService;
import andrew.samardak.spring_aop.service.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionServiceImpl implements TransactionService {

    AccountService accountService;
    TransactionRepository transactionRepository;
    TransactionMapper transactionMapper;

    @Override
    public Transaction create(TransactionRequestDto dto) {
        Long accountId = dto.getAccountId();

        Account account = accountService.getRepository().findById(accountId).orElseThrow();

        Transaction transaction = transactionMapper.toEntity(dto);
        transaction.setAccount(account);

        return transactionRepository.save(transaction);
    }

    @Override
    public JpaRepository<Transaction, Long> getRepository() {
        return transactionRepository;
    }

    @Override
    public BaseMapper<Transaction, TransactionRequestDto, TransactionResponseDto> getMapper() {
        return transactionMapper;
    }
}
