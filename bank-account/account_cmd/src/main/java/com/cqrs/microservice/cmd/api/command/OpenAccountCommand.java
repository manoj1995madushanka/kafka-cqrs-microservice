package com.cqrs.microservice.cmd.api.command;

import com.cqrs.microservice.commands.BaseCommand;
import com.cqrs.microservice.dto.AccountType;
import lombok.Data;

@Data
public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;
}
