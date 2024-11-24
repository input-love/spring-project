package andrew.samardak.spring_aop.kafka.consumer;

import andrew.samardak.spring_aop.dto.request.AccountRequestDto;
import andrew.samardak.spring_aop.facade.AccountFacade;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaAccountConsumer {

    AccountFacade accountFacade;

    @KafkaListener(
            id = "${spring-project.kafka.topics.consumer.accounts.id}",
            topics = "${spring-project.kafka.topics.consumer.accounts.name}",
            containerFactory = "kafkaAccountListenerContainerFactory"
    )
    public void listener(
            @Payload AccountRequestDto dto
    ) {
        accountFacade.create(dto);
    }
}
