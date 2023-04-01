package com.bank.account.query.api.controllers;

import com.bank.account.query.api.dto.AccountLookupResponse;
import com.bank.account.query.api.dto.EqualityType;
import com.bank.account.query.api.queries.*;
import com.bank.account.query.domain.BankAccount;
import com.bank.cqrs.core.infrastructure.QueryDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/accountLookup")
public class AccountLookupController {
    private final Logger logger = Logger.getLogger(AccountLookupController.class.getName());

    @Autowired
    private QueryDispatcher queryDispatcher;

    @GetMapping(path = "/")
    public ResponseEntity<AccountLookupResponse> getAllAccounts() {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAllAccounts());
            if (accounts.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            AccountLookupResponse response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Successfully return {0} bank accounts", accounts.size()))
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            String errorMsg = "Failed to get all accounts";
            logger.log(Level.SEVERE, errorMsg, e);
            return new ResponseEntity<>(new AccountLookupResponse(errorMsg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byId/{id}")
    public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable(value = "id") String id) {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountById(id));
            if (accounts.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            AccountLookupResponse response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message("Successfully returned bank account")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            String errorMsg = "Failed to get account by id";
            logger.log(Level.SEVERE, errorMsg, e);
            return new ResponseEntity<>(new AccountLookupResponse(errorMsg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byHolder/{holder}")
    public ResponseEntity<AccountLookupResponse> getAccountByHolder(@PathVariable(value = "holder") String holder) {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByHolder(holder));
            if (accounts.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            AccountLookupResponse response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message("Successfully returned bank account")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            String errorMsg = "Failed to get account by holder";
            logger.log(Level.SEVERE, errorMsg, e);
            return new ResponseEntity<>(new AccountLookupResponse(errorMsg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/withBalance/{equalityType}/{balance}")
    public ResponseEntity<AccountLookupResponse> getAccountByHolder(@PathVariable(value = "equalityType") EqualityType equalityType,
                                                                    @PathVariable(value = "balance") double balance) {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountWithBalance(equalityType, balance));
            if (accounts.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            AccountLookupResponse response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message("Successfully returned bank accounts")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            String errorMsg = "Failed to get account by balance";
            logger.log(Level.SEVERE, errorMsg, e);
            return new ResponseEntity<>(new AccountLookupResponse(errorMsg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
