package andrew.samardak.spring_aop.dto.response;

import andrew.samardak.spring_aop.utils.enums.TransactionStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponseDto {

    private Long id;

    private TransactionStatus transactionStatus;

    private BigDecimal amount;

    private LocalDateTime transactionTime;

    private Long accountId;
}
