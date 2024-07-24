package com.group1.app.repository;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.group1.app.entity.Nasabah;
import com.group1.app.entity.enums.AccountStatus;

public class InMemoryRepository implements Repository, Closeable {
    private List<Nasabah> nasabahList = new ArrayList<>();

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
    public List<Nasabah> getDataNasabahByAccountStatus(AccountStatus status) {
        return this.nasabahList.stream().filter(n -> n.getAccountStatus() == status).toList();
    }

    @Override
    public boolean approveNasabahByNIK(String NIK) {
        Nasabah nasabah = this.nasabahList.stream().filter(n -> n.getNIK().equals(NIK)).findFirst().orElse(null);
        if (nasabah != null) {
            nasabah.setAccountStatus(AccountStatus.ACTIVE);
            return true;
        }
        return false;
    }

    @Override
    public boolean approveAllPendingActiveNasabah() {
        this.nasabahList.stream().filter(n -> n.getAccountStatus() == AccountStatus.PENDING_ACTIVE)
                .forEach(n -> n.setAccountStatus(AccountStatus.ACTIVE));
        return true;
    }

    @Override
    public void close() throws IOException {
        nasabahList.clear();
    }
}
