package andrew.samardak.spring_aop.aspect;

import andrew.samardak.spring_aop.entity.DataSourceErrorLog;
import andrew.samardak.spring_aop.kafka.producer.KafkaProducerService;
import andrew.samardak.spring_aop.service.LogDataSourceErrorService;
import andrew.samardak.spring_aop.utils.constants.KafkaHeaderConstants;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LogDataSourceErrorAspect {

    LogDataSourceErrorService service;

    KafkaProducerService<DataSourceErrorLog> kafkaProducerService;

    @Pointcut("execution(* (@LogDataSourceError *).*(..))")
    public void methodsInAnnotatedClass() {
    }

    @AfterThrowing(
            pointcut = "methodsInAnnotatedClass()",
            throwing = "exception",
            argNames = "joinPoint,exception"
    )
    public void afterThrowing(JoinPoint joinPoint, Exception exception) {
        DataSourceErrorLog message = buildMessage(joinPoint, exception);
        Map<String, String> header = buildHeader();

        CompletableFuture<SendResult<String, DataSourceErrorLog>> future = kafkaProducerService.sendMessage(
                "t1_demo_metrics", message, header
        );

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message=[ {} ] with offset=[ {} ]", message, result.getRecordMetadata().offset());
            } else {
                service.create(message);

                log.error("Unable to send message=[ {} ] due to : ", ex.getMessage(), ex);
            }
        });
    }

    private Map<String, String> buildHeader() {
        return Map.of(
                KafkaHeaderConstants.ERROR_TYPE_HEADER, KafkaHeaderConstants.ERROR_DATA_SOURCE_VALUE
        );
    }

    private DataSourceErrorLog buildMessage(JoinPoint joinPoint, Exception exception) {
        return DataSourceErrorLog.builder()
                .stackTrace(Arrays.toString(exception.getStackTrace()))
                .message(exception.getMessage())
                .methodSignature(joinPoint.getSignature().toLongString()).build();
    }
}
