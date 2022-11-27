package com.cqrs.microservice.infrastructure;

import com.cqrs.microservice.commands.BaseCommand;
import com.cqrs.microservice.commands.CommandHandlerMethod;

/*
* this is mediator that manages the distribution of messages(command) among their objects
* */
public interface CommandDispatcher {

    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);

    void send(BaseCommand command);
}
