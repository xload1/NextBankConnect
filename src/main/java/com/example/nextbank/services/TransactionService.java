package com.example.nextbank.services;

import com.example.nextbank.model.Transactions;
import com.example.nextbank.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    TransactionsRepository transactionsRepository;
    UserService userService;

    public TransactionService(TransactionsRepository transactionsRepository, UserService userService) {
        this.transactionsRepository = transactionsRepository;
        this.userService = userService;
    }

    @Autowired
    public TransactionService(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;

    }

    public void saveTransaction(Transactions transaction) {
        transactionsRepository.save(transaction);
    }
    public List<String> getFormattedTransactions(int user_id) {
        return transactionsRepository.findAllByUser1_idAndUser2_id(user_id).stream().map(e ->{
            switch (e.getPurpose()){
                case
            }
        }).toList();
    }
}
