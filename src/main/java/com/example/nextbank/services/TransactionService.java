package com.example.nextbank.services;

import com.example.nextbank.model.Transactions;
import com.example.nextbank.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return transactionsRepository.findAllByUser1idAndUser2id(user_id, user_id).stream().map(e->{
            if(e.getUser1id()==user_id){
                if(e.getUser2id()==0){
                    return "Withdrawn: " + "-" + e.getAmount() + "\n" +
                            "Balance: " + userService.getUserById(e.getUser1id()).getBalance() + "\n" +
                            "Date: " + e.getDate().format(formatter) + "\n" +
                            "Purpose: " + e.getPurpose();
                }
                else{
                    return "Sent: " + "-" + e.getAmount() + "\n" +
                            "Balance: " + userService.getUserById(e.getUser1id()).getBalance() + "\n" +
                            "Date: " + e.getDate().format(formatter) + "\n" +
                            "Purpose: " + e.getPurpose();
                }
            }else if(e.getUser2id()==user_id){
                if(e.getUser1id() == 0){
                    return "Deposited: " + "+" + e.getAmount() + "\n" +
                            "Balance: " + userService.getUserById(e.getUser2id()).getBalance() + "\n" +
                            "Date: " + e.getDate().format(formatter) + "\n" +
                            "Purpose: " + e.getPurpose();
                }else{
                return "Received: " + "+" + e.getAmount() + "\n" +
                        "Balance: " + userService.getUserById(e.getUser2id()).getBalance() + "\n" +
                        "Date: " + e.getDate().format(formatter) + "\n" +
                        "Purpose: " + e.getPurpose();
                }
            } else{
                return "Wrong transaction!";
            }
        }).toList();

    }
}
