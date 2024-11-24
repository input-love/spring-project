package andrew.samardak.spring_aop.kafka.consumer;

import andrew.samardak.spring_aop.dto.request.TransactionResultRequestDto;
import andrew.samardak.spring_aop.facade.TransactionFacade;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaTransactionResultConsumer {

    TransactionFacade transactionFacade;

    @KafkaListener(
            id = "${spring-project.kafka.topics.consumer.transactions-result.id}",
            topics = "${spring-project.kafka.topics.consumer.transactions-result.name}",
            containerFactory = "kafkaTransactionResultListenerContainerFactory"
    )
    public void listener(
            @Payload TransactionResultRequestDto dto
    ) {
        transactionFacade.handleTransaction(dto);
    }
}
