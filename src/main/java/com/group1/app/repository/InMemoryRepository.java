package com.group1.app.repository;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.group1.app.entity.Account;
import com.group1.app.entity.Nasabah;
import com.group1.app.entity.enums.AccountRoles;
import com.group1.app.entity.enums.NasabahStatus;

public class InMemoryRepository implements Repository, Closeable {
    private List<Nasabah> nasabahList = new ArrayList<>();
    private List<Account> accountList = new ArrayList<>(
            List.of(new Account("admin@admin.com", "admin", AccountRoles.ADMIN)));

    @Override
    public boolean saveDataNasabah(Nasabah n) {
        Nasabah nasabahExist = this.nasabahList.stream().filter(nasabah -> nasabah.getNIK().equals(n.getNIK()))
                .findFirst()
                .orElse(null);

        if (nasabahExist == null) {
            this.nasabahList.add(n);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Nasabah> getAllDataNasabah() {
        return this.nasabahList;
    }

    @Override
    public List<Nasabah> getDataNasabahByAccountStatus(NasabahStatus status) {
        return this.nasabahList.stream().filter(n -> n.getAccountStatus() == status).toList();
    }

    @Override
    public boolean approveNasabahByNIK(String NIK) {
        Nasabah nasabah = this.nasabahList.stream().filter(n -> n.getNIK().equals(NIK)).findFirst().orElse(null);
        if (nasabah != null) {
            nasabah.setAccountStatus(NasabahStatus.ACTIVE);
            return true;
        }
        return false;
    }

    @Override
    public boolean approveAllPendingActiveNasabah() {
        this.nasabahList.stream().filter(n -> n.getAccountStatus() == NasabahStatus.PENDING_ACTIVE)
                .forEach(n -> n.setAccountStatus(NasabahStatus.ACTIVE));
        return true;
    }

    @Override
    public boolean validateAccount(Account account) {
        Account accountExist = this.accountList.stream().filter(acc -> {
            return acc.getEmail().equals(account.getEmail()) && acc.getRole() == account.getRole();
        }).findFirst().orElse(null);

        if (accountExist != null) {
            return accountExist.getPassword().equals(account.getPassword());
        } else {
            return false;
        }
    }

    @Override
    public void close() throws IOException {
        nasabahList.clear();
    }
}
