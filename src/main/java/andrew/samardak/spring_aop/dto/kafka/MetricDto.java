package andrew.samardak.spring_aop.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MetricDto {

    private long executionTimeMillis;

    private Object[] parameters;

    private String methodSignature;
}