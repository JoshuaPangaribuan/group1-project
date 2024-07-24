package com.group1.app.entity;

import com.group1.app.entity.enums.NasabahStatus;

public class Nasabah {
    private String NIK;
    private String Nama;
    private Account Account;
    private NasabahStatus AccountStatus;
    // BANK ENTITY

    public Nasabah() {
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

}
