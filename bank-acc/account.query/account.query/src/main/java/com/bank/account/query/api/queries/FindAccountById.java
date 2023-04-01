package com.bank.account.query.api.queries;

import com.bank.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountById extends BaseQuery {
    private String id;
}
