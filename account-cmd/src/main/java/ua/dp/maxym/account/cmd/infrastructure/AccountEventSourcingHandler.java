package ua.dp.maxym.account.cmd.infrastructure;

import org.springframework.stereotype.Service;
import ua.dp.maxym.account.cmd.domain.AccountAggregate;
import ua.dp.maxym.cqrs.core.handlers.EventSourcingHandler;
import ua.dp.maxym.cqrs.core.infrastructure.EventStore;
import ua.dp.maxym.cqrs.core.producers.EventProducer;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    private final EventStore eventStore;
    private final EventProducer eventProducer;

    public AccountEventSourcingHandler(EventStore eventStore, EventProducer eventProducer) {
        this.eventStore = eventStore;
        this.eventProducer = eventProducer;
    }

    @Override
    public void save(AccountAggregate aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String aggregateId) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvents(aggregateId);
        // Returning uninitialized aggregate. TODO: I think it's wrong but this is in the course...
        if (events == null || events.isEmpty())
            return aggregate;

        aggregate.replayEvents(events);
        // WTF why so complex?
        // var latestVersion = events.stream().map(BaseEvent::getVersion).max(Comparator.naturalOrder()).get();
        var latestVersion = events.get(events.size() - 1).getVersion();
        aggregate.setVersion(latestVersion);
        return aggregate;
    }

    @Override
    public void republishEvents() {
        var aggregateIds = eventStore.getAggregateIds();
        aggregateIds.forEach(aggregateId -> {
            var aggregate = getById(aggregateId);
            if (aggregate==null || !aggregate.isActive())
                return;

            var aggregateEvents = eventStore.getEvents(aggregateId);
            aggregateEvents.forEach(event -> eventProducer.produce(event.getClass().getSimpleName(), event));
        });
    }
}
