package com.group1.app.repository;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.group1.app.entity.Nasabah;

public class InMemoryRepository implements Repository, Closeable {
    private List<Nasabah> nasabahList = new ArrayList<>();

    @Override
    public boolean saveDataNasabah(Nasabah n) {
        return nasabahList.add(n);
    }

    @Override
    public List<Nasabah> getAllDataNasabah() {
        return this.nasabahList;
    }

    @Override
    public void close() throws IOException {
        nasabahList.clear();
    }
}
