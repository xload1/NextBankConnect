package com.example.nextbank.services;

import com.example.nextbank.enums.Purpose;
import com.example.nextbank.enums.Roles;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.text.DecimalFormat;
import java.text.ParseException;

@Service
public class OperationHelper {

    public double getResultAmount(double amount, Purpose purpose, Roles role) throws ParseException {
        double commission = 0.0;
        switch (purpose) {
            case WITHDRAW -> commission = 0.05;
            case DEPOSIT -> commission = 0.0;
            case TRANSFER -> commission = 0.02;
            case PAYMENT, GIFT -> commission = 0.01;
        }
        switch (role){
            case SILVER -> commission += 0.05;
            case GOLDEN -> commission += 0.02;
            case PLATINUM -> commission += 0.00;
        }
        return formatFunds(amount - (amount * commission));
    }
    public Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name))
                    return cookie;
            }
        }
        return null;
    }
    public void saveCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 10);
        response.addCookie(cookie);
    }
    public void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
    public String passwordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), Files.readAllBytes(Path.of("C:\\Users\\marat\\IdeaProjects\\NextBank Connect\\salt.txt")), 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return new String(hash);
    }
    public double formatFunds(double funds) throws ParseException {
        return new DecimalFormat("#.##").format(funds).equals("-0") ? 0.0 : new DecimalFormat("#.##").parse(new DecimalFormat("#.##").format(funds)).doubleValue();
    }
}
