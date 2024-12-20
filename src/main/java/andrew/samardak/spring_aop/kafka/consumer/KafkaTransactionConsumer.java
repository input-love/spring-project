package andrew.samardak.spring_aop.kafka.consumer;

import andrew.samardak.spring_aop.dto.request.TransactionRequestDto;
import andrew.samardak.spring_aop.service.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaTransactionConsumer {

    TransactionService service;

    @KafkaListener(
            id = "${spring-project.kafka.topic.transactions.id}",
            topics = "${spring-project.kafka.topic.transactions.name}",
            containerFactory = "kafkaTransactionListenerContainerFactory"
    )
    public void listener(
            @Payload TransactionRequestDto dto
    ) {
        service.create(dto);
    }
}
