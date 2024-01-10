package com.example.nextbank.model;

import com.example.nextbank.enums.Purpose;
import com.example.nextbank.services.UserService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transaction_id;
    private int user1_id;
    private int user2_id;
    @Enumerated(EnumType.STRING)
    private Purpose purpose;
    @NotNull
    @Positive
    private double amount;
    private LocalDate date;

    public Transactions(int user1_id, int user2_id, Purpose purpose, double amount) {
        this.user1_id = user1_id;
        this.user2_id = user2_id;
        this.purpose = purpose;
        this.amount = amount;
        this.date = java.time.LocalDate.now();
    }

    public void setUser1_id(int user1_id) {
        this.user1_id = user1_id;
    }

    public void setUser2_id(int user2_id) {
        this.user2_id = user2_id;
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

}
