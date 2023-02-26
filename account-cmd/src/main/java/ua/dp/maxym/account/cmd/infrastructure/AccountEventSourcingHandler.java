package ua.dp.maxym.account.cmd.infrastructure;

import org.springframework.stereotype.Service;
import ua.dp.maxym.account.cmd.domain.AccountAggregate;
import ua.dp.maxym.cqrs.core.handlers.EventSourcingHandler;
import ua.dp.maxym.cqrs.core.infrastructure.EventStore;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    private final EventStore eventStore;

    public AccountEventSourcingHandler(EventStore eventStore) {
        this.eventStore = eventStore;
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
}
