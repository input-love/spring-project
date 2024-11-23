package andrew.samardak.spring_aop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MetricResponseDto {

    private long executionTimeMillis;

    private Object[] parameters;

    private String methodSignature;
}