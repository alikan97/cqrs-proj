package com.bank.cqrs.core.handlers;

import com.bank.cqrs.core.domain.AggregateRoot;

public interface EventSourcingHandler <T>{
    void save(AggregateRoot aggregate);
    T getById(String id);
    void republishEvents();
}
