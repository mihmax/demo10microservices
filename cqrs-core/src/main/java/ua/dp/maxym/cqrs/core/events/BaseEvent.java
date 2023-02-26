package ua.dp.maxym.cqrs.core.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.dp.maxym.cqrs.core.messages.BaseMessage;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseEvent extends BaseMessage {
    private int version;
}
