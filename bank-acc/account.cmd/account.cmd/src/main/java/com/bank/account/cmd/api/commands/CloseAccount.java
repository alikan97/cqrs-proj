package com.bank.account.cmd.api.commands;

import com.bank.cqrs.core.commands.BaseCommand;

public class CloseAccount extends BaseCommand {
    public CloseAccount(String id) {
        super(id);
    }
}
