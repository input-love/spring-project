package andrew.samardak.spring_aop.mappers;

import andrew.samardak.spring_aop.dto.request.TransactionRequestDto;
import andrew.samardak.spring_aop.dto.response.TransactionResponseDto;
import andrew.samardak.spring_aop.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper extends BaseMapper<Transaction, TransactionRequestDto, TransactionResponseDto> {

    @Override
    @Mapping(source = "account.id", target = "accountId")
    TransactionResponseDto toDto(Transaction transaction);
}
