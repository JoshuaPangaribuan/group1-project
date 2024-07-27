package com.group1.app.entity;

import java.util.List;

public class Bank {
    private String name;
    private String label;
    private List<BankAccount> bankAccounts;
    
    public Bank(String name, String label, List<BankAccount> bankAccounts) {
        this.name = name;
        this.label = label;
        this.bankAccounts = bankAccounts;
    }

    public Bank() {}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }
    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    
}
