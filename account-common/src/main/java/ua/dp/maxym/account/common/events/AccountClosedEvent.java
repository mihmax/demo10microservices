package ua.dp.maxym.account.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.dp.maxym.account.common.dto.AccountType;
import ua.dp.maxym.cqrs.core.events.BaseEvent;

import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class AccountClosedEvent extends BaseEvent {
}
