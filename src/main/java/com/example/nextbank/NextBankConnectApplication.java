package com.example.nextbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;

@SpringBootApplication
public class NextBankConnectApplication {

    public static void main(String[] args){
        SpringApplication.run(NextBankConnectApplication.class, args);
    }

}
