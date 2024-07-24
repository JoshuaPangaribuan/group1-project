package com.group1.app.repository;

import java.util.List;

import com.group1.app.entity.Nasabah;
import com.group1.app.entity.enums.AccountStatus;

public interface Repository {
    boolean saveDataNasabah(Nasabah n);

    List<Nasabah> getAllDataNasabah();

    List<Nasabah> getDataNasabahByAccountStatus(AccountStatus status);

    boolean approveNasabahByNIK(String NIK);

    boolean approveAllPendingActiveNasabah();
}
