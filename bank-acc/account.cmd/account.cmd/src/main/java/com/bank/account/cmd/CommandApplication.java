package com.bank.account.cmd;

import com.bank.account.cmd.api.commands.*;
import com.bank.cqrs.core.infrastructure.CommandDispatcher;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommandApplication {
	@Autowired
	private CommandDispatcher commandDispatcher;

	@Autowired
	private CommandHandler commandHandler;

	public static void main(String[] args) {
		SpringApplication.run(CommandApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers() {
		commandDispatcher.registerHandler(OpenAccount.class, commandHandler::handle);
		commandDispatcher.registerHandler(DepositFunds.class, commandHandler::handle);
		commandDispatcher.registerHandler(Withdraw.class, commandHandler::handle);
		commandDispatcher.registerHandler(CloseAccount.class, commandHandler::handle);
	}
}
