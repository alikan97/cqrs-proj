package com.bank.account.query.api.queries;

import com.bank.cqrs.core.domain.BaseEntity;

import java.util.List;

public interface QueryHandler {
    List<BaseEntity> handle(FindAllAccounts query);
    List<BaseEntity> handle(FindAccountById query);
    List<BaseEntity> handle(FindAccountWithBalance query);
    List<BaseEntity> handle(FindAccountByHolder query);
}
