package com.cqrs.microservice.commands;

@FunctionalInterface
public interface CommandHandlerMethod<T extends BaseCommand> {

    /*
    * functional interface should have only one abstract method
    * */
    void handle(T t);

}
