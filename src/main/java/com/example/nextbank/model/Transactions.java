package com.example.nextbank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transaction_id;
    private int user1_id;
    private int user2_id;
    private String purpose;
    private double amount;
    private LocalDate date;

    public Transactions(int user1_id, int user2_id, String purpose, double amount) {
        this.user1_id = user1_id;
        this.user2_id = user2_id;
        this.purpose = purpose;
        this.amount = amount;
        this.date = java.time.LocalDate.now();
    }
}
