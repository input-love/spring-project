package andrew.samardak.spring_aop.service.impl;

import andrew.samardak.spring_aop.dto.request.AccountRequestDto;
import andrew.samardak.spring_aop.dto.response.AccountResponseDto;
import andrew.samardak.spring_aop.entity.Account;
import andrew.samardak.spring_aop.mappers.AccountMapper;
import andrew.samardak.spring_aop.mappers.BaseMapper;
import andrew.samardak.spring_aop.repository.AccountRepository;
import andrew.samardak.spring_aop.service.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {

    AccountRepository repository;
    AccountMapper mapper;

    @Override
    public JpaRepository<Account, Long> getRepository() {
        return repository;
    }

    @Override
    public BaseMapper<Account, AccountRequestDto, AccountResponseDto> getMapper() {
        return mapper;
    }
}
