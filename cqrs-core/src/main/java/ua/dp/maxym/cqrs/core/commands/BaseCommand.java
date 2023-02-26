package ua.dp.maxym.cqrs.core.commands;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.dp.maxym.cqrs.core.messages.BaseMessage;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public abstract class BaseCommand extends BaseMessage {
    public BaseCommand(String id) {
        super(id);
    }
}
