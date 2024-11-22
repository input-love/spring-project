package andrew.samardak.spring_aop.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionAcceptDto {

    private Long clientId;

    private Long accountId;

    private Long transactionId;

    private LocalDateTime timestamp;

    private TransactionDetails transaction;

    private AccountDetails account;

    @Data
    @AllArgsConstructor
    public static class TransactionDetails {
        private BigDecimal amount;
    }

    @Data
    @AllArgsConstructor
    public static class AccountDetails {
        private BigDecimal balance;
    }
}
