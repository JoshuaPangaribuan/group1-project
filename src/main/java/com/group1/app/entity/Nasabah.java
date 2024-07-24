package com.group1.app.entity;

import com.group1.app.entity.enums.AccountStatus;

public class Nasabah {
    private String NIK;
    private String Nama;
    private String Email;
    private String Password;
    private AccountStatus AccountStatus;
    // BANK ENTITY

    public Nasabah(String NIK, String Nama, String Email, String Password) {
        this.NIK = NIK;
        this.Nama = Nama;
        this.Email = Email;
        this.Password = Password;
    }

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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public AccountStatus getAccountStatus() {
        return AccountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        AccountStatus = accountStatus;
    }

}
