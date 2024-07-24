package com.group1.app.entity;

import com.group1.app.entity.enums.AccountRoles;

public class Account {
    private String email;
    private String password;
    private AccountRoles role;

    public Account(String email, String password, AccountRoles role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Account() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountRoles getRole() {
        return role;
    }

    public void setRole(AccountRoles role) {
        this.role = role;
    }

}
