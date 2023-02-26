package ua.dp.maxym.account.query.api.queries;

import ua.dp.maxym.cqrs.core.domain.BaseEntity;

import java.util.List;

public interface QueryHandler {
    List<BaseEntity> handle(FindAllAccountsQuery query);
    List<BaseEntity> handle(FindAccountsByIdQuery query);
    List<BaseEntity> handle(FindAccountsByHolderQuery query);
    List<BaseEntity> handle(FindAccountsWithBalanceQuery query);
}
