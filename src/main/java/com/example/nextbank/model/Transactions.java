package com.example.nextbank.model;

import com.example.nextbank.enums.Purpose;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
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

    public Transactions() {
        this.date = java.time.LocalDate.now();
    }
}
