package com.example.nextbank.model;

import com.example.nextbank.enums.Purpose;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transaction_id;
    @Column(name = "user1_id")
    private int user1id;
    @Column(name = "user2_id")
    private int user2id;
    @Enumerated(EnumType.STRING)
    private Purpose purpose;
    @NotNull
    @Positive(message = "Amount must be positive")
    private double amount;
    @Column(name = "transaction_date")
    private LocalDate date;
    private double amount_after;

//    public Transactions(int user1id, int user2id, Purpose purpose, double amount) {
//        this.user1id = user1id;
//        this.user2id = user2id;
//        this.purpose = purpose;
//        this.amount = amount;
//        this.date = java.time.LocalDate.now();
//    }

    public Transactions() {
        this.date = java.time.LocalDate.now();
    }

    public void setUser1id(int user1_id) {
        this.user1id = user1_id;
    }

    public void setUser2id(int user2_id) {
        this.user2id = user2_id;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    public void setAmount(double amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException();
        }
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = LocalDate.parse(date);
    }

    public void setAmount_after(double amount_after) {
        this.amount_after = amount_after;
    }
}
