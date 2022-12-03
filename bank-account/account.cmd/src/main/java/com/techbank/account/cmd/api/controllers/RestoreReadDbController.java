package com.techbank.account.cmd.api.controllers;

import com.techbank.account.cmd.api.commands.RestoreReadDbCommand;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

@RestController
@RequestMapping(path = "api/v1/restoreReadDb")
public class RestoreReadDbController {

    private final Logger logger = LoggerFactory.getLogger(RestoreReadDbController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> restoreReadDb() {
        try {
            commandDispatcher.send(new RestoreReadDbCommand());
            return new ResponseEntity<>(new BaseResponse("Restored database successfully"), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.warn(MessageFormat.format("Client made a bad request - {0}.", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            String errMessage = "Error while restoring read database.";
            logger.error(errMessage, e);
            return new ResponseEntity<>(new BaseResponse(errMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
