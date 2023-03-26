package com.bank.account.common.events;

import com.bank.cqrs.core.events.BaseEvent;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class AccountClosed extends BaseEvent {
}
