package com.example.nextbank.services;

import org.springframework.stereotype.Service;

@Service
public class CommissionHelper {
    public double getResultAmount(double amount, String purpose, String role) {
        double commission = 0.0;
        switch (purpose) {
            case "Withdrawal" -> commission = 0.05;
            case "Deposit" -> commission = 0.0;
            case "Basic Transfer" -> commission = 0.02;
            case "Payment", "Gift" -> commission = 0.01;
        }
        switch (role){
            case "SILVER" -> commission += 0.05;
            case "GOLDEN" -> commission += 0.02;
            case "PLATINUM" -> commission += 0.00;
        }
        return amount - (amount * commission);
    }
}
