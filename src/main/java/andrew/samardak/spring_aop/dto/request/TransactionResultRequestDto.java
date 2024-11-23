package andrew.samardak.spring_aop.dto.request;

import andrew.samardak.spring_aop.utils.enums.TransactionStatus;
import lombok.Data;

@Data
public class TransactionResultRequestDto {

    private Long accountId;

    private Long transactionId;

    TransactionStatus transactionStatus;
}
