package com.bank.account.query.api.queries;

import com.bank.account.query.api.dto.EqualityType;
import com.bank.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountWithBalance extends BaseQuery {
    private EqualityType equalityType;
    private double balance;
}
