package ua.dp.maxym.account.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.dp.maxym.cqrs.core.events.BaseEvent;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FundsDepositedEvent extends BaseEvent {
    private double depositedAmount;
}
