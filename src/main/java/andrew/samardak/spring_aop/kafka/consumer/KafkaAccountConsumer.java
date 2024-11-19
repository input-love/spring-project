package andrew.samardak.spring_aop.kafka.consumer;

import andrew.samardak.spring_aop.dto.request.AccountRequestDto;
import andrew.samardak.spring_aop.service.AccountService;
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

    AccountService service;

    @KafkaListener(
            id = "${spring-project.kafka.topic.accounts.id}",
            topics = "${spring-project.kafka.topic.accounts.name}",
            containerFactory = "kafkaAccountListenerContainerFactory"
    )
    public void listener(
            @Payload AccountRequestDto dto
    ) {
        service.create(dto);
    }
}
