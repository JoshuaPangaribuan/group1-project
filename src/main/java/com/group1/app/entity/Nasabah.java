package com.group1.app.entity;

import java.util.Map;

import com.group1.app.entity.enums.NasabahStatus;

public class Nasabah {
    private String NIK;
    private String Nama;
    private Account Account;
    private NasabahStatus AccountStatus;
    private Map<String, String> bankAccounts; // KEY = Bank Label, VALUE = Account Number

    public Nasabah() {
    }

    public Nasabah(String NIK, String nama, Account account, NasabahStatus accountStatus,
            Map<String, String> bankAccounts) {
        this.NIK = NIK;
        this.Nama = nama;
        this.Account = account;
        this.AccountStatus = accountStatus;
        this.bankAccounts = bankAccounts;
    }

    public String getNIK() {
        return NIK;
    }

    public void setNIK(String nIK) {
        NIK = nIK;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public NasabahStatus getAccountStatus() {
        return AccountStatus;
    }

    public void setAccountStatus(NasabahStatus accountStatus) {
        AccountStatus = accountStatus;
    }

    public Account getAccount() {
        return Account;
    }

    public void setAccount(Account account) {
        Account = account;
    }

    public Map<String, String> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(Map<String, String> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

}
