package com.group1;

import com.group1.app.Application;
import com.group1.app.BankAggregatorApp;

public class Main {
    public static void main(String[] args) {
        Application app = new BankAggregatorApp();
        try {
            app.Start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}