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
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int comment_id;
    private int feedback_id;
    private int user_id;
    private String heading;
    private String comment_text;
    private LocalDate date;

    public Comments(int feedback_id, int user_id, String heading, String comment_text) {
        this.feedback_id = feedback_id;
        this.user_id = user_id;
        this.heading = heading;
        this.comment_text = comment_text;
        this.date = java.time.LocalDate.now();
    }
}
