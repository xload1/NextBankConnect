package com.example.nextbank.model;

import jakarta.persistence.*;
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
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    @Column(name = "user_name")
    private String name;
    @Column(name = "middle_name")
    private String middleName;
    private String surname;
    private String email;
    private String password;
    private String role;
    @Column(name = "birth_date")
    private LocalDate date;
    private double balance;
    public Users(String name, String middleName, String surname, String email, String password) {
        this.name = name;
        this.middleName = middleName;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.balance = 0.0;
        this.date = LocalDate.now();
        this.role = "SILVER";
    }
}
