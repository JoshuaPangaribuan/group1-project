package com.group1.app.repository;

import java.util.List;

import com.group1.app.entity.Nasabah;

public interface Repository {
    boolean saveDataNasabah(Nasabah n);

    List<Nasabah> getAllDataNasabah();
}
