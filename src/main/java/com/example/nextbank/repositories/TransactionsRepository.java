package com.example.nextbank.repositories;

import com.example.nextbank.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {
    public List<Transactions> findAllByUser1_idAndUser2_id(int transaction_id);
}
