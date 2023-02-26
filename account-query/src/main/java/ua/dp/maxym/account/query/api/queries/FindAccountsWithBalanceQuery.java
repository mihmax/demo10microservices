package ua.dp.maxym.account.query.api.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.dp.maxym.account.query.api.dto.EqualityType;
import ua.dp.maxym.cqrs.core.queries.BaseQuery;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class FindAccountsWithBalanceQuery extends BaseQuery {
    private final EqualityType equalityType;
    private final BigDecimal balance;
}
