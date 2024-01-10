package com.example.nextbank.controllers;

import com.example.nextbank.model.Transactions;
import com.example.nextbank.model.Users;
import com.example.nextbank.services.OperationHelper;
import com.example.nextbank.services.TransactionService;
import com.example.nextbank.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    UserService userService;
    TransactionService transactionService;
    OperationHelper operationHelper;

    @Autowired
    public TransactionController(UserService userService, TransactionService transactionService, OperationHelper operationHelper) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.operationHelper = operationHelper;
    }


    @GetMapping("/new")
    public String createNewTransaction() {
        return "new transaction page";
    }

    @PutMapping("/new")
    public ResponseEntity<String> newTransaction(@Valid @RequestBody Transactions transaction) throws IllegalAccessException {
        double resultAmount;
        //handling the case when the user sends money to himself
        if (transaction.getUser1id() == transaction.getUser2id())
            throw new IllegalArgumentException("You can't send money to yourself");
        //getting the result amount after the fees
        if (transaction.getUser1id() != 0)
            resultAmount = operationHelper.getResultAmount(transaction.getAmount(), transaction.getPurpose(), userService.getUserById(transaction.getUser1id()).getRole());
        else
            resultAmount = operationHelper.getResultAmount(transaction.getAmount(), transaction.getPurpose(), userService.getUserById(transaction.getUser2id()).getRole());
        transaction.setAmount_after(resultAmount);
        //transaction logic
        switch (transaction.getPurpose()) {
            case TRANSFER, GIFT, PAYMENT -> {
                if (userService.getUserById(transaction.getUser1id()).getBalance() - resultAmount < 0)
                    throw new IllegalAccessException();
                userService.saveUser(new Users(userService.getUserById(transaction.getUser1id()).getBalance() - transaction.getAmount(),
                        userService.getUserById(transaction.getUser1id())));
                userService.saveUser(new Users(userService.getUserById(transaction.getUser2id()).getBalance() + resultAmount,
                        userService.getUserById(transaction.getUser2id())));
                transactionService.saveTransaction(transaction);
            }
            case DEPOSIT -> {
                userService.saveUser(new Users(userService.getUserById(transaction.getUser2id()).getBalance() + resultAmount,
                        userService.getUserById(transaction.getUser2id())));
                transactionService.saveTransaction(transaction);
            }
            case WITHDRAW -> {
                if (userService.getUserById(transaction.getUser1id()).getBalance() - resultAmount < 0)
                    throw new IllegalAccessException();
                userService.saveUser(new Users(userService.getUserById(transaction.getUser1id()).getBalance() - resultAmount,
                        userService.getUserById(transaction.getUser1id())));
                transactionService.saveTransaction(transaction);
            }
        }
        return ResponseEntity.ok("transaction successful");
    }

    @GetMapping("/")
    public List<String> getTransactions(HttpServletRequest request) {
        return transactionService.getFormattedTransactions(Integer.parseInt(operationHelper.getCookie(request, "user_id").getValue()));
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(IllegalAccessException e) {
        return ResponseEntity.badRequest().body("Not enough funds to proceed " + e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body("Amount must be greater than 0 " + e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.badRequest().body("Something went wrong " + e.getMessage());
    }
}
