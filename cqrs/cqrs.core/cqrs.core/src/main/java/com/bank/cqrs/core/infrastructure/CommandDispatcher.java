package com.bank.cqrs.core.infrastructure;

import com.bank.cqrs.core.commands.BaseCommand;
import com.bank.cqrs.core.commands.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class<T> Type, CommandHandlerMethod<T> handlerMethod);
    void send(BaseCommand command);
}
