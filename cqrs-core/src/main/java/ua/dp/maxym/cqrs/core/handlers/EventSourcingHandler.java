package ua.dp.maxym.cqrs.core.handlers;

import ua.dp.maxym.cqrs.core.domain.AggregateRoot;

public interface EventSourcingHandler<T extends AggregateRoot> {
    void save(T aggregate);
    T getById(String aggregateId);
    void republishEvents();
}
