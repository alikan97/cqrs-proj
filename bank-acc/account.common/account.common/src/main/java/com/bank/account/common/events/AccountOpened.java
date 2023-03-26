package com.bank.account.common.events;

import com.bank.account.common.dto.AccountTypes;
import com.bank.cqrs.core.events.BaseEvent;

import java.util.Date;

public class AccountOpened extends BaseEvent {
    private String accountHolder;
    private AccountTypes accountType;
    private Date createdDate;
    private double openingBalance;
}
