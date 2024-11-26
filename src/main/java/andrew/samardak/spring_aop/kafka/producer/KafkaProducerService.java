package andrew.samardak.spring_aop.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class KafkaProducerService<V> {

    private final KafkaTemplate<String, V> kafkaTemplate;

    public CompletableFuture<SendResult<String, V>> sendMessage(
            String topic,
            V message,
            Map<String, String> headers
    ) {
        Headers recordHeaders = new RecordHeaders();
        headers.forEach((key, value) ->
                recordHeaders.add(new RecordHeader(key, value.getBytes(StandardCharsets.UTF_8)))
        );

        ProducerRecord<String, V> record = new ProducerRecord<>(
                topic,
                null,
                null,
                UUID.randomUUID().toString(),
                message,
                recordHeaders
        );

        return kafkaTemplate.send(record);
    }
}
