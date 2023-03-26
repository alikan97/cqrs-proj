package com.bank.account.cmd.api.commands;

import com.bank.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class Withdraw extends BaseCommand {
    private double amount;
}
