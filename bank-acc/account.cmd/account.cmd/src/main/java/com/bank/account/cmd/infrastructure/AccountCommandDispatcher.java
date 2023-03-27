package com.bank.account.cmd.infrastructure;

import com.bank.cqrs.core.commands.BaseCommand;
import com.bank.cqrs.core.commands.CommandHandlerMethod;
import com.bank.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {
    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> Type, CommandHandlerMethod<T> handlerMethod) {
        var handlers = routes.computeIfAbsent(Type, c -> new LinkedList<>());
        handlers.add(handlerMethod);
    }

    @Override
    public void send(BaseCommand command) {
        var handlers = routes.get(command.getClass());
        if (handlers == null || handlers.size() == 0) {
            throw new RuntimeException("Uh oh");
        }
        if (handlers.size() > 1) {
            throw new RuntimeException("Cannot send command to more than one handler");
        }
        handlers.get(0).handle(command);
    }
}
