package ua.dp.maxym.account.cmd.infrastructure;

import org.springframework.stereotype.Service;
import ua.dp.maxym.account.cmd.domain.AccountAggregate;
import ua.dp.maxym.account.cmd.domain.EventStoreRepository;
import ua.dp.maxym.cqrs.core.events.BaseEvent;
import ua.dp.maxym.cqrs.core.events.EventModel;
import ua.dp.maxym.cqrs.core.infrastructure.EventStore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccoutEventStore implements EventStore {

    private final EventStoreRepository eventStoreRepository;

    public AccoutEventStore(EventStoreRepository eventStoreRepository) {
        this.eventStoreRepository = eventStoreRepository;
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
            if (persistedEvent != null) {
                // TODO: produce event to Kafka
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
}
