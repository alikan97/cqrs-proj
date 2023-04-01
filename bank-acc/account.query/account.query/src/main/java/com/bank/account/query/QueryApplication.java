package com.bank.account.query;

import com.bank.account.query.api.queries.*;
import com.bank.cqrs.core.infrastructure.QueryDispatcher;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QueryApplication {
	@Autowired
	private QueryDispatcher queryDispatcher;

	@Autowired
	private QueryHandler queryHandler;

	public static void main(String[] args) {
		SpringApplication.run(QueryApplication.class, args);
	}

	@PostConstruct
	public void registerQueryHandler() {
		queryDispatcher.registerHandler(FindAllAccounts.class, queryHandler::handle);
		queryDispatcher.registerHandler(FindAccountWithBalance.class, queryHandler::handle);
		queryDispatcher.registerHandler(FindAccountByHolder.class, queryHandler::handle);
		queryDispatcher.registerHandler(FindAccountById.class, queryHandler::handle);
	}
}
