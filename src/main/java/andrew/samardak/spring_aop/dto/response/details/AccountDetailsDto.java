package andrew.samardak.spring_aop.dto.response.details;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AccountDetailsDto {

    private BigDecimal balance;
}
