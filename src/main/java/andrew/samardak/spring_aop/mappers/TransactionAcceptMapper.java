package andrew.samardak.spring_aop.mappers;

import andrew.samardak.spring_aop.dto.response.TransactionAcceptResponseDto;
import andrew.samardak.spring_aop.entity.Account;
import andrew.samardak.spring_aop.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = LocalDateTime.class)
public interface TransactionAcceptMapper {

    @Mapping(source = "account.client.id", target = "clientId")
    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "transaction.id", target = "transactionId")
    @Mapping(target = "timestamp", expression = "java(LocalDateTime.now())")
    @Mapping(target = "transaction", expression = "java(new TransactionAcceptResponseDto.TransactionDetails(transaction.getAmount()))")
    @Mapping(target = "account", expression = "java(new TransactionAcceptResponseDto.AccountDetails(account.getBalance()))")
    TransactionAcceptResponseDto toTransactionAcceptDto(Account account, Transaction transaction);
}
