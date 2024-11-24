package andrew.samardak.spring_aop.dto.response;

import andrew.samardak.spring_aop.dto.response.details.AccountDetailsDto;
import andrew.samardak.spring_aop.dto.response.details.TransactionDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionAcceptResponseDto {

    private Long clientId;

    private Long accountId;

    private Long transactionId;

    private LocalDateTime timestamp;

    private AccountDetailsDto account;

    private TransactionDetailsDto transaction;
}
