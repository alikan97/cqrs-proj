package com.bank.account.cmd.api.commands;

import com.bank.account.common.dto.AccountTypes;
import com.bank.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class OpenAccount extends BaseCommand {
    private String accountHolder;
    private AccountTypes accountType;
    private double openingBalance;
}
