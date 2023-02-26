package ua.dp.maxym.account.common.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.dp.maxym.cqrs.core.events.BaseEvent;


@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class AccountClosedEvent extends BaseEvent {
}
