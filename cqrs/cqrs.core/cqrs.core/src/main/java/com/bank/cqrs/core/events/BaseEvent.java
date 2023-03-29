package com.bank.cqrs.core.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.bank.cqrs.core.messages.Message;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseEvent extends Message {           // Failing because it can't find Message, therefore id field
    private int version;        // Used for optimistic locking
}
