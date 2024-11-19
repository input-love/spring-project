package andrew.samardak.spring_aop.dto.response;

import andrew.samardak.spring_aop.utils.enums.AccountType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponseDto {

    private Long id;

    private AccountType accountType;

    private BigDecimal balance;
}
