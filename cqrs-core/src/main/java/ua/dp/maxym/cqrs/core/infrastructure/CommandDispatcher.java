package ua.dp.maxym.cqrs.core.infrastructure;

import ua.dp.maxym.cqrs.core.commands.BaseCommand;
import ua.dp.maxym.cqrs.core.commands.CommandHandlerMethod;
import ua.dp.maxym.cqrs.core.events.BaseEvent;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);
    void send(BaseCommand command);
}
