package andrew.samardak.spring_aop.kafka.consumer;

import andrew.samardak.spring_aop.dto.request.TransactionRequestDto;
import andrew.samardak.spring_aop.facade.TransactionFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaTransactionConsumer {

    private final TransactionFacade transactionFacade;

    @KafkaListener(
            id = "${spring-project.kafka.topics.consumer.transactions.id}",
            topics = "${spring-project.kafka.topics.consumer.transactions.name}",
            containerFactory = "kafkaTransactionListenerContainerFactory"
    )
    public void listener(
            @Payload TransactionRequestDto dto
    ) {
        transactionFacade.processTransaction(dto);
    }
}
