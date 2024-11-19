package andrew.samardak.spring_aop.service.impl;

import andrew.samardak.spring_aop.dto.request.TransactionRequestDto;
import andrew.samardak.spring_aop.dto.response.TransactionResponseDto;
import andrew.samardak.spring_aop.entity.Transaction;
import andrew.samardak.spring_aop.mappers.BaseMapper;
import andrew.samardak.spring_aop.mappers.TransactionMapper;
import andrew.samardak.spring_aop.repository.TransactionRepository;
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

    TransactionRepository repository;
    TransactionMapper mapper;

    @Override
    public JpaRepository<Transaction, Long> getRepository() {
        return repository;
    }

    @Override
    public BaseMapper<Transaction, TransactionRequestDto, TransactionResponseDto> getMapper() {
        return mapper;
    }
}
