package ua.dp.maxym.account.query.api.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.dp.maxym.cqrs.core.queries.BaseQuery;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class FindAccountsByIdQuery extends BaseQuery {
    private final String id;
}
