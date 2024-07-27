package com.group1.app.entity;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private String accountNumber;
    private String accountName;
    private String pin;
    private String NIK;
    private Double balance;
    private Double holdBalance;
    private List<TransferHistory> transferHistories;

    public BankAccount(String accountNumber, String accountName, String pin, String nIK, Double balance,
            Double holdBalance) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.pin = pin;
        NIK = nIK;
        this.balance = balance;
        this.holdBalance = holdBalance;
        this.transferHistories = new ArrayList<>();
    }

    public BankAccount() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getHoldBalance() {
        return holdBalance;
    }

    public void setHoldBalance(Double holdBalance) {
        this.holdBalance = holdBalance;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getNIK() {
        return NIK;
    }

    public void setNIK(String nIK) {
        NIK = nIK;
    }

    public List<TransferHistory> getTransferHistories() {
        return transferHistories;
    }

    public void setTransferHistories(List<TransferHistory> transferHistories) {
        this.transferHistories = transferHistories;
    }

    public void addTransferHistory(TransferHistory transferHistory) {
        this.transferHistories.add(transferHistory);
    }
}
