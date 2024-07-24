package com.group1.app.repository;

import java.util.List;

import com.group1.app.entity.Account;
import com.group1.app.entity.Nasabah;
import com.group1.app.entity.enums.NasabahStatus;

public interface Repository {
    boolean saveDataNasabah(Nasabah n);

    List<Nasabah> getAllDataNasabah();

    List<Nasabah> getDataNasabahByAccountStatus(NasabahStatus status);

    boolean approveNasabahByNIK(String NIK);

    boolean approveAllPendingActiveNasabah();

    boolean validateAccount(Account account);
}
