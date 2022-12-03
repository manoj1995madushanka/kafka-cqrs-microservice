package com.techbank.account.query.api.controller;

import com.techbank.account.query.api.dto.AccountLookupResponse;
import com.techbank.account.query.api.dto.EqualityType;
import com.techbank.account.query.api.queries.FindAccountByHolderQuery;
import com.techbank.account.query.api.queries.FindAccountByIdQuery;
import com.techbank.account.query.api.queries.FindAccountWithBalanceQuery;
import com.techbank.account.query.api.queries.FindAllAccountsQuery;
import com.techbank.account.query.domain.BankAccount;
import com.techbank.cqrs.core.infrastructure.QueryDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/bankAccountLookup")
public class AccountLookupController {

    private final Logger logger = LoggerFactory.getLogger(AccountLookupController.class.getSimpleName());

    @Autowired
    private QueryDispatcher queryDispatcher;

    @GetMapping(path = "/")
    public ResponseEntity<AccountLookupResponse> getAllAccounts() {
        try {
            List<BankAccount> bankAccountList = queryDispatcher.send(new FindAllAccountsQuery());
            if (bankAccountList == null || bankAccountList.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            AccountLookupResponse response = AccountLookupResponse.builder()
                    .accounts(bankAccountList)
                    .message(MessageFormat.format("Successfully returned {0} bank account(s)", bankAccountList.size()))
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            String errMsg = "Failed to complete get all account request";
            logger.error(errMsg, e);
            return new ResponseEntity<>(new AccountLookupResponse(errMsg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byId/{id}")
    public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable(value = "id") String id) {
        try {
            List<BankAccount> bankAccountList = queryDispatcher.send(new FindAccountByIdQuery(id));
            if (bankAccountList == null || bankAccountList.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            AccountLookupResponse response = AccountLookupResponse.builder()
                    .accounts(bankAccountList)
                    .message("Successfully returned bank account")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            String errMsg = "Failed to complete get account by id request";
            logger.error(errMsg, e);
            return new ResponseEntity<>(new AccountLookupResponse(errMsg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byHolder/{accountHolder}")
    public ResponseEntity<AccountLookupResponse> getAccountByAccountHolder(@PathVariable(value = "accountHolder") String accountHolder) {
        try {
            List<BankAccount> bankAccountList = queryDispatcher.send(new FindAccountByHolderQuery(accountHolder));
            if (bankAccountList == null || bankAccountList.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            AccountLookupResponse response = AccountLookupResponse.builder()
                    .accounts(bankAccountList)
                    .message("Successfully returned bank account")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            String errMsg = "Failed to complete get account by holder request";
            logger.error(errMsg, e);
            return new ResponseEntity<>(new AccountLookupResponse(errMsg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/withBalance/{equalityType}/{balance}")
    public ResponseEntity<AccountLookupResponse> getAccountWithBalance(@PathVariable(value = "equalityType") EqualityType equalityType,
                                                                       @PathVariable(value = "balance") Double balance) {
        try {
            List<BankAccount> bankAccountList = queryDispatcher.send(new FindAccountWithBalanceQuery(balance,equalityType));
            if (bankAccountList == null || bankAccountList.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            AccountLookupResponse response = AccountLookupResponse.builder()
                    .accounts(bankAccountList)
                    .message("Successfully returned bank account")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            String errMsg = "Failed to complete get account with balance request";
            logger.error(errMsg, e);
            return new ResponseEntity<>(new AccountLookupResponse(errMsg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
