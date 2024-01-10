package com.example.nextbank.controllers;

import com.example.nextbank.model.DTOs.LoginRequest;
import com.example.nextbank.model.Users;
import com.example.nextbank.services.OperationHelper;
import com.example.nextbank.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/authentication")
public class AuthController {
    OperationHelper operationHelper;
    UserService userService;
    @Autowired
    public AuthController(OperationHelper operationHelper, UserService userService) {
        this.operationHelper = operationHelper;
        this.userService = userService;
    }
    @GetMapping("/")
    public ResponseEntity<Void> index() {
        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .location(java.net.URI.create(ServletUriComponentsBuilder.fromUriString("/authentication/login").build().toUriString()))
                .build();
    }

    @GetMapping("/login")
    public String login() {
        return "login page";
    }
    @GetMapping("/register")
    public String register() {
        return "register page";
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody Users user, HttpServletResponse response) {
        operationHelper.saveCookie(response, "user", user.getUser_id() + "");
        userService.saveUser(user);
        return ResponseEntity.ok("redirect:/profile");
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Users user = userService.getUserByEmail(loginRequest.getEmail());
        if(user == null)
            throw new IllegalArgumentException("User not found");
        if(!operationHelper.passwordHash(user.getPassword()).equals(loginRequest.getPassword()))
            throw new IllegalArgumentException("Wrong password");
        operationHelper.saveCookie(response, "user", userService.getUserByEmail(loginRequest.getEmail()).getUser_id() + "");
        return ResponseEntity.ok("redirect:/profile");
    }
    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        operationHelper.deleteCookie(response, "user");
        return ResponseEntity.ok("redirect:/login");
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
