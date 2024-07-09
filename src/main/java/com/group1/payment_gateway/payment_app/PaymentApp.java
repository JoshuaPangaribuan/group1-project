package com.group1.payment_gateway.payment_app;

import com.group1.payment_gateway.common.uid.IUIDGenerator;
import com.group1.payment_gateway.common.uid.UIDNumberGeneratorBuilder;

public class PaymentApp implements IApp {
    public void Start() {
        IUIDGenerator<Integer> generator = new UIDNumberGeneratorBuilder<Integer>().setLength(10).build();
        System.out.println("Payment app started with UID generator: " + generator.Generate().toString());
    }

    public void Stop() {
        System.out.println("Payment app stopped");
    }
}