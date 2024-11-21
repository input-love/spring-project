package andrew.samardak.spring_aop.mappers;

import andrew.samardak.spring_aop.dto.request.AccountRequestDto;
import andrew.samardak.spring_aop.dto.response.AccountResponseDto;
import andrew.samardak.spring_aop.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper extends BaseMapper<Account, AccountRequestDto, AccountResponseDto> {

    @Override
    @Mapping(source = "client.id", target = "clientId")
    AccountResponseDto toDto(Account account);
}
