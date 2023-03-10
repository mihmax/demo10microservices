package ua.dp.maxym.account.cmd.infrastructure;

import org.springframework.stereotype.Service;
import ua.dp.maxym.account.cmd.domain.AccountAggregate;
import ua.dp.maxym.account.cmd.domain.EventStoreRepository;
import ua.dp.maxym.cqrs.core.events.BaseEvent;
import ua.dp.maxym.cqrs.core.events.EventModel;
import ua.dp.maxym.cqrs.core.infrastructure.EventStore;
import ua.dp.maxym.cqrs.core.producers.EventProducer;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccoutEventStore implements EventStore {

    private final EventStoreRepository eventStoreRepository;
    private final EventProducer eventProducer;

    public AccoutEventStore(EventStoreRepository eventStoreRepository, EventProducer eventProducer) {
        this.eventStoreRepository = eventStoreRepository;
        this.eventProducer = eventProducer;
    }

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (expectedVersion != -1 && eventStream.get(eventStream.size()-1).getVersion() != expectedVersion)
            throw new ConcurrencyException("Wrong expected version");

        var version = expectedVersion;
        for (var event: events) {
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .timeStamp(LocalDateTime.now())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();
            var persistedEvent = eventStoreRepository.save(eventModel);
            if (!persistedEvent.getId().isEmpty()) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (eventStream == null || eventStream.isEmpty())
            throw new AggregateNotFoundException("Incorrect account id provided " + aggregateId);

        return eventStream.stream().map(EventModel::getEventData).toList();
    }

    @Override
    public List<String> getAggregateIds() {
        var eventStream = eventStoreRepository.findAll();
        if (eventStream == null || eventStream.isEmpty())
            throw new IllegalStateException("Could not retrieve event stream from the event store");
        return eventStream.stream().map(EventModel::getAggregateIdentifier).distinct().toList();
    }
}
