package com.example.nextbank.controllers;

import com.example.nextbank.controllers.DTOs.LoginRequest;
import com.example.nextbank.model.Users;
import com.example.nextbank.services.OperationHelper;
import com.example.nextbank.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public String index() {
        return "redirect:/login";
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
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
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
