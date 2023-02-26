package ua.dp.maxym.cqrs.core.producers;

import ua.dp.maxym.cqrs.core.events.BaseEvent;

public interface EventProducer {
    void produce(String topicName, BaseEvent event);
}
