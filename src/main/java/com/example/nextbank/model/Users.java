package com.example.nextbank.model;

import com.example.nextbank.services.OperationHelper;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.grammars.hql.HqlParser;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
    private String password;
    private String role;
    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birth_date;
    private double balance;
    public Users(String name, String middleName, String surname, String email, String password, String birth_date) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.name = name;
        this.middleName = middleName;
        this.surname = surname;
        this.email = email;
        this.password = new OperationHelper().passwordHash(password);
        this.balance = 0.0;
        this.birth_date = LocalDate.parse(birth_date);
        this.role = "SILVER";
    }
}
