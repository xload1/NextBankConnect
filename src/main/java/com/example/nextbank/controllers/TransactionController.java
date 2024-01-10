package com.example.nextbank.controllers;

import com.example.nextbank.model.Transactions;
import com.example.nextbank.model.Users;
import com.example.nextbank.services.TransactionService;
import com.example.nextbank.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.example.nextbank.enums.Purpose.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    UserService userService;
    TransactionService transactionService;
    @Autowired
    public TransactionController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping("/new")
    public String createNewTransaction() {
        return "new transaction page";
    }
    @PutMapping("/new")
    public ResponseEntity<String> newTransaction(@Valid @RequestBody Transactions transaction) throws IllegalAccessException {
        switch (transaction.getPurpose()){
            case TRANSFER, GIFT, PAYMENT->{ if (userService.getUserById(transaction.getUser1_id()).getBalance()-transaction.getAmount() < 0)
                throw new IllegalAccessException();
                userService.saveUser(new Users(userService.getUserById(transaction.getUser1_id()).getBalance()-transaction.getAmount(),
                        userService.getUserById(transaction.getUser1_id())));
                userService.saveUser(new Users(userService.getUserById(transaction.getUser2_id()).getBalance()+transaction.getAmount(),
                        userService.getUserById(transaction.getUser2_id())));
                transactionService.saveTransaction(transaction);
            }

            }
        }
        return ResponseEntity.ok("new transaction");
    }
    @ExceptionHandler
    public ResponseEntity<String> handleException(IllegalAccessException e) {
        return ResponseEntity.badRequest().body("Amount must be greater than 0 " + e.getMessage());
    }
}
