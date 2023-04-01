package com.bank.account.cmd.api.controllers;

import com.bank.account.cmd.api.commands.OpenAccount;
import com.bank.account.cmd.api.commands.RestoreReadDb;
import com.bank.account.cmd.api.dto.OpenAccountResponse;
import com.bank.account.common.dto.BaseResponse;
import com.bank.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/restore")
public class RestoreReadDbController {
    private final Logger logger = Logger.getLogger(RestoreReadDbController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> restoreReadDb () {
        try {
            commandDispatcher.send(new RestoreReadDb());
            return new ResponseEntity<>(new BaseResponse("Bank account created successfully"), HttpStatus.CREATED);
        } catch(IllegalStateException e) {
            logger.log(Level.WARNING, MessageFormat.format("Invalid client request - {0}", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing request to open bank account for id - {0}";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
