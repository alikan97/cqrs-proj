package com.bank.account.cmd.domain;

import com.bank.account.cmd.api.commands.OpenAccount;
import com.bank.account.common.events.AccountClosed;
import com.bank.account.common.events.AccountOpened;
import com.bank.account.common.events.FundWithdrawn;
import com.bank.account.common.events.FundsDeposited;
import com.bank.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    public Boolean active;
    public double balance;

    public AccountAggregate (OpenAccount command) {
        raiseEvent(AccountOpened.builder()
                .accountType(command.getAccountType())
                .accountHolder(command.getAccountHolder())
                .openingBalance(command.getOpeningBalance())
                .createdDate(new Date())
                .build());
    }

    public void apply (AccountOpened event) {
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(double amount) {
        if (!this.active) {
            throw new IllegalStateException("Funds cannot be deposited into close acc");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit must be > 0");
        }

        raiseEvent(FundsDeposited.builder().amount(amount).build());
    }

    public void apply (FundsDeposited events) {
        this.id = events.getId();
        this.balance += events.getAmount();
    }

    public void withdrawFunds (double amount) {
        if (!this.active) throw new IllegalStateException("Cannot withdraw from closed acc");

        raiseEvent(FundWithdrawn.builder().amount(amount).build());
    }

    public void apply (FundWithdrawn event) {
        this.id = event.getId();
        this.balance -= event.getAmount();
    }

    public void closeAccount () {
        if (!this.active) throw new IllegalStateException("Account already closed");

        raiseEvent(AccountClosed.builder().build());
    }

    public void apply (AccountClosed event) {
        this.id = event.getId();
        this.active = false;
    }
}
