package com.bank.account.cmd.api.controllers;

import com.bank.account.cmd.api.commands.CloseAccount;
import com.bank.account.cmd.api.commands.DepositFunds;
import com.bank.account.common.dto.BaseResponse;
import com.bank.cqrs.core.exceptions.AggregateNotFoundException;
import com.bank.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/close")
public class CloseAccountController {
    private final Logger logger = Logger.getLogger(CloseAccountController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable(value = "id") String id) {
        try {
            commandDispatcher.send(new CloseAccount(id));
            return new ResponseEntity(new BaseResponse("Bank account closed successfully"), HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException e) {
            return new ResponseEntity(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            String errorMsg = MessageFormat.format("Error processing request for id - {0}", id);
            logger.log(Level.SEVERE, errorMsg, e);
            return new ResponseEntity(new BaseResponse(errorMsg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
