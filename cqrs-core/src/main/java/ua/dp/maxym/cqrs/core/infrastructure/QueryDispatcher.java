package ua.dp.maxym.cqrs.core.infrastructure;

import ua.dp.maxym.cqrs.core.domain.BaseEntity;
import ua.dp.maxym.cqrs.core.queries.BaseQuery;
import ua.dp.maxym.cqrs.core.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);
    <U extends BaseEntity> List<U> send(BaseQuery query);
}
