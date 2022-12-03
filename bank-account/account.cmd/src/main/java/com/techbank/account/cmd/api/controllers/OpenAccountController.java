package com.techbank.account.cmd.api.controllers;

import com.techbank.account.cmd.api.commands.OpenAccountCommand;
import com.techbank.account.cmd.api.dto.OpenAccountResponse;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RestController
@RequestMapping(path = "api/v1/openBankAccount")
public class OpenAccountController {

    private final Logger logger = LoggerFactory.getLogger(OpenAccountController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command) {
        String id = UUID.randomUUID().toString();
        command.setId(id);

        /*
        *
        *
        *
        * */
        try {
            commandDispatcher.send(command);
            return new ResponseEntity<>(new OpenAccountResponse("Bank account created successfully", id), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.warn(MessageFormat.format("Client made a bad request - {0}.", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            String errMessage = MessageFormat.format("Error while processing request to open a new bank account for id -{0}.", id);
            logger.error(errMessage, e);
            return new ResponseEntity<>(new OpenAccountResponse(errMessage, id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
