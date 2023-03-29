package com.bank.account.cmd.api.commands;

public interface CommandHandler {
    void handle(OpenAccount command);
    void handle(CloseAccount command);
    void handle(Withdraw command);
    void handle(DepositFunds command);
}
