package com.bank.account.common.events;

import com.bank.account.common.dto.AccountTypes;
import com.bank.cqrs.core.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AccountOpened extends BaseEvent {
    private String accountHolder;
    private AccountTypes accountType;
    private Date createdDate;
    private double openingBalance;
}
