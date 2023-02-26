package ua.dp.maxym.account.cmd.infrastructure;

import org.springframework.stereotype.Service;
import ua.dp.maxym.cqrs.core.commands.BaseCommand;
import ua.dp.maxym.cqrs.core.commands.CommandHandlerMethod;
import ua.dp.maxym.cqrs.core.infrastructure.CommandDispatcher;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {
    @SuppressWarnings("rawtypes")
    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();
    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, i -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public void send(BaseCommand command) {
        var handlers = routes.get(command.getClass());
        if (handlers == null || handlers.size()==0)
            throw new RuntimeException("No handlers are registered for " + command.getClass().getName());
        if (handlers.size()>1)
            throw new RuntimeException("Cannot send commands more than to one handler for " + command.getClass().getName());
        handlers.get(0).handle(command);
    }
}
