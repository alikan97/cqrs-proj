package com.bank.account.cmd.api.controllers;

import com.bank.account.cmd.api.commands.DepositFunds;
import com.bank.account.cmd.api.commands.Withdraw;
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
@RequestMapping(path = "/api/v1/withdraw")
public class WithdrawFundsController {
    private final Logger logger = Logger.getLogger(WithdrawFundsController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> withdrawFunds(@PathVariable(value = "id") String id, @RequestBody Withdraw command) {
        try {
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity(new BaseResponse("Funds withdrawn successfully"), HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException e) {
            return new ResponseEntity(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            String errorMsg = MessageFormat.format("Error processing request for id - {0}", id);
            logger.log(Level.SEVERE, errorMsg, e);
            return new ResponseEntity(new BaseResponse(errorMsg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
