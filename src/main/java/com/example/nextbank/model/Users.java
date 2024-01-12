package com.example.nextbank.model;


import com.example.nextbank.enums.Roles;
import com.example.nextbank.services.OperationHelper;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import org.aspectj.lang.annotation.After;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;

import static com.example.nextbank.enums.Roles.SILVER;

@Entity
@Getter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    @NotNull(message = "Name is required")
    @Column(name = "user_name")
    private String name;
    @Null
    @Column(name = "middle_name")
    private String middleName;
    @NotNull(message = "Surname is required")
    private String surname;
    @NotNull(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;
    @NotNull(message = "Password is required")
    @Size(min = 4, message = "Password must be at least 4 characters")
    private String password;
    @Enumerated(EnumType.STRING)
    private Roles role;
    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birth_date;
    private double balance;

    public Users() {
        this.role = SILVER;
        this.balance = 0.0;
    }

    public Users(double balance, Users user) {
        this.balance = balance;
        this.name = user.getName();
        this.middleName = user.getMiddleName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.birth_date = user.getBirth_date();
        this.user_id = user.getUser_id();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        this.password = new OperationHelper().passwordHash(password);
    }

    public void setRole() {
        this.role = SILVER;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
