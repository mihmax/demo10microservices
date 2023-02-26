package ua.dp.maxym.account.query.infrastructure.queries;

import org.springframework.stereotype.Service;
import ua.dp.maxym.cqrs.core.domain.BaseEntity;
import ua.dp.maxym.cqrs.core.infrastructure.QueryDispatcher;
import ua.dp.maxym.cqrs.core.queries.BaseQuery;
import ua.dp.maxym.cqrs.core.queries.QueryHandlerMethod;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {

    @SuppressWarnings("rawtypes")
    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes =
            new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, i -> new LinkedList<>());
        handlers.add(handler);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U extends BaseEntity> List<U> send(BaseQuery query) {
        var handlers = routes.get(query.getClass());
        if (handlers == null || handlers.size() == 0)
            throw new RuntimeException("No query handler was registered for " + query.getClass().getName());
        if (handlers.size() > 1)
            throw new RuntimeException("NCannot send query to more than one handler for " + query.getClass()
                                                                                                 .getName() + ", but we have registered " + handlers);

        return handlers.get(0).handle(query);
    }
}
