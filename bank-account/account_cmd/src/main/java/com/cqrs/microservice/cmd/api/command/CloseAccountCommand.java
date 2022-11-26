package com.cqrs.microservice.cmd.api.command;

import com.cqrs.microservice.commands.BaseCommand;

public class CloseAccountCommand extends BaseCommand {

    public CloseAccountCommand(String id) {
        super(id);
    }
}
