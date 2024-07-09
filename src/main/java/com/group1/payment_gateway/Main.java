package com.group1.payment_gateway;

import com.group1.payment_gateway.payment_app.PaymentApp;

public class Main {
    public static void main(String[] args) {
        PaymentApp app = new PaymentApp();
        app.Start();
    }
}