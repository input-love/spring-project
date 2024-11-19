package andrew.samardak.spring_aop.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequestDto {

    private BigDecimal amount;
}
