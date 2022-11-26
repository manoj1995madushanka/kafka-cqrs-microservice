package com.cqrs.microservice.cmd.api.command;

import com.cqrs.microservice.commands.BaseCommand;
import lombok.Data;

@Data
public class DepositFundsCommand extends BaseCommand {
    private double amount;
}
