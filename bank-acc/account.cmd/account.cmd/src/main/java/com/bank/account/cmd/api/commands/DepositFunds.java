package com.bank.account.cmd.api.commands;

import com.bank.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class DepositFunds extends BaseCommand {
    private double amount;
}
