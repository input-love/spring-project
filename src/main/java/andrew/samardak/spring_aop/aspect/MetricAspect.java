package andrew.samardak.spring_aop.aspect;

import andrew.samardak.spring_aop.dto.kafka.MetricDto;
import andrew.samardak.spring_aop.kafka.producer.KafkaMetricProducer;
import andrew.samardak.spring_aop.utils.constants.KafkaHeaderConstants;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MetricAspect {

    KafkaMetricProducer<MetricDto> producer;

    @Around(
            value = "@annotation(metric)",
            argNames = "pJoinPoint,metric"
    )
    public Object measureExecutionTime(ProceedingJoinPoint pJoinPoint, Metric metric) throws Throwable {
        Instant startTime = Instant.now();

        Object result = null;
        try {
            result = pJoinPoint.proceed();
        } finally {
            long duration = Duration.between(startTime, Instant.now()).toMillis();

            if (duration > metric.value()) {
                MetricDto message = buildMessage(pJoinPoint, duration);
                Map<String, String> header = buildHeader();

                producer.sendMessage(message, header);
            }
        }

        return result;
    }

    private Map<String, String> buildHeader() {
        return Map.of(
                KafkaHeaderConstants.ERROR_TYPE_HEADER, KafkaHeaderConstants.ERROR_METRIC_VALUE
        );
    }

    private MetricDto buildMessage(ProceedingJoinPoint pJoinPoint, long duration) {
        return new MetricDto(
                duration,
                pJoinPoint.getArgs(),
                pJoinPoint.getSignature().toLongString()
        );
    }
}
