package andrew.samardak.spring_aop.dto.request;

import andrew.samardak.spring_aop.utils.enums.TransactionStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequestDto {

    private TransactionStatus transactionStatus;

    private BigDecimal amount;
}
