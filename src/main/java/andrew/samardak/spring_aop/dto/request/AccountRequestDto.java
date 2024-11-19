package andrew.samardak.spring_aop.dto.request;

import andrew.samardak.spring_aop.utils.enums.AccountType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequestDto {

    private AccountType accountType;

    private BigDecimal balance;
}
