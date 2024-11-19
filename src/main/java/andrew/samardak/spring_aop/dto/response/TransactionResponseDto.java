package andrew.samardak.spring_aop.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponseDto {

    private Long id;

    private BigDecimal amount;

    private LocalDateTime transactionTime;
}
