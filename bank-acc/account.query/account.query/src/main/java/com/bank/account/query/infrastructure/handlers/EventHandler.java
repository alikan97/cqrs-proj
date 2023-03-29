package com.bank.account.query.infrastructure.handlers;

import com.bank.account.common.events.AccountClosed;
import com.bank.account.common.events.AccountOpened;
import com.bank.account.common.events.FundWithdrawn;
import com.bank.account.common.events.FundsDeposited;

public interface EventHandler {
    void on(AccountOpened event);
    void on(AccountClosed event);
    void on(FundsDeposited event);
    void on(FundWithdrawn event);
}
