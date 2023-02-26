package ua.dp.maxym.cqrs.core.queries;

import ua.dp.maxym.cqrs.core.domain.BaseEntity;

import java.util.List;

@FunctionalInterface
public interface QueryHandlerMethod<T extends BaseQuery> {
    List<BaseEntity> handle(T query);
}
