package ua.dp.maxym.account.cmd.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ua.dp.maxym.cqrs.core.events.BaseEvent;
import ua.dp.maxym.cqrs.core.producers.EventProducer;

@Service
public class AccountEventProducer implements EventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public AccountEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void produce(String topicName, BaseEvent event) {
        kafkaTemplate.send(topicName, event);
    }
}
