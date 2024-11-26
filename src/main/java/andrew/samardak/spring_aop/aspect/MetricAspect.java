package andrew.samardak.spring_aop.aspect;

import andrew.samardak.spring_aop.dto.response.MetricResponseDto;
import andrew.samardak.spring_aop.kafka.producer.KafkaProducerService;
import andrew.samardak.spring_aop.utils.constants.KafkaHeaderConstants;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
public class MetricAspect {

    @Value("${spring-project.kafka.topics.producer.metrics}")
    private String topic;

    private final KafkaProducerService<MetricResponseDto> kafkaProducerService;

    @Around(
            value = "@annotation(metric)",
            argNames = "pJoinPoint,metric"
    )
    public Object measureExecutionTime(ProceedingJoinPoint pJoinPoint, Metric metric) throws Throwable {
        Instant startTime = Instant.now();

        Object result;
        try {
            result = pJoinPoint.proceed();
        } finally {
            long duration = Duration.between(startTime, Instant.now()).toMillis();

            if (duration > metric.value()) {
                MetricResponseDto message = buildMessage(pJoinPoint, duration);
                Map<String, String> header = buildHeader();

                kafkaProducerService.sendMessage(topic, message, header);
            }
        }

        return result;
    }

    private Map<String, String> buildHeader() {
        return Map.of(
                KafkaHeaderConstants.ERROR_TYPE_HEADER, KafkaHeaderConstants.ERROR_METRIC_VALUE
        );
    }

    private MetricResponseDto buildMessage(ProceedingJoinPoint pJoinPoint, long duration) {
        return new MetricResponseDto(
                duration,
                pJoinPoint.getArgs(),
                pJoinPoint.getSignature().toLongString()
        );
    }
}
