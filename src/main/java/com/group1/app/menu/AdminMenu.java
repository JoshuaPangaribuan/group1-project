package com.group1.app.menu;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import com.group1.app.entity.Nasabah;
import com.group1.app.entity.enums.AccountStatus;
import com.group1.app.menu.enums.MenuNavigation;
import com.group1.app.repository.Repository;
import com.group1.common.exception.NoopException;

final public class AdminMenu implements Menu {

    Scanner scan;
    private boolean runningState = true;
    private boolean optionState = true;
    Repository repository;

    public AdminMenu(Scanner scan, Repository repository) {
        this.repository = repository;
        this.scan = scan;
    }

    @Override
    public Exception execute() {
        while (runningState) {
            display();

            while (optionState) {
                Integer option = Integer.parseInt(this.scan.nextLine());

                switch (option) {
                    case 1:
                        optionState = false;
                        approveDataNasabahFlow();
                        break;

                    case 2:
                        optionState = false;
                        tampilkanDataNasabah(this.repository.getDataNasabahByAccountStatus(AccountStatus.ACTIVE));
                        break;

                    case 3:
                        // tampilkanDataTransaksi();
                        break;

                    case 4:
                        // tampilkanDataRekening();
                        break;

                    case 5:
                        return new NoopException("no operation!");

                    default:
                        System.out.println("Tolong masukkan input yang benar!");
                        optionState = false;
                        break;
                }

                if (!optionState) {
                    break;
                }
            }

            if (!optionState && runningState) {
                optionState = true;
                continue;
            }
        }

        return new Exception("unknown error");
    }

    private void display() {
        System.out.println("\nSelamat Datang di Menu Simulasi Admin!");
        System.out.println("1. Approve Data Nasabah");
        System.out.println("2. Tampilkan Nasabah Aktif");
        System.out.println("3. Lihat Data Transaksi");
        System.out.println("4. Lihat Data Rekening");
        System.out.println("5. Kembali ke menu sebelumnya");
        System.out.print("Pilih Menu: ");
    }

    private void approveDataNasabahFlow() {
        boolean approveState = true;

        while (approveState) {
            boolean done = tampilkanDataNasabah(
                    this.repository.getDataNasabahByAccountStatus(AccountStatus.PENDING_ACTIVE));

            if (!done)
                return;

            System.out.println("Pilih Metode Approve: ");
            System.out.println("1. Approve Semua Nasabah");
            System.out.println("2. Approve Nasabah Berdasarkan NIK");
            System.out.println("3. Kembali ke menu sebelumnya");
            System.out.print("Pilih Menu: ");

            Integer option = Integer.parseInt(this.scan.nextLine());

            switch (option) {
                case 1:
                    approveAllPendingActiveNasabah();
                    approveState = false;
                    break;

                case 2:
                    approveNasabahByNIK();
                    List<Nasabah> remaining = this.repository
                            .getDataNasabahByAccountStatus(AccountStatus.PENDING_ACTIVE);
                    if (remaining.isEmpty()) {
                        System.out.println("Tidak ada Nasabah Berstatus PENDING_ACTIVE yang tersisa!");
                        approveState = false;
                    }
                    break;

                case 3:
                    approveState = false;
                    break;

                default:
                    System.out.println("Tolong masukkan input yang benar!");
                    break;
            }
        }
    }

    private void approveAllPendingActiveNasabah() {
        this.repository.approveAllPendingActiveNasabah();
        System.out.println("Semua Nasabah Berstatus PENDING_ACTIVE Berhasil Diapprove!");
    }

    private void approveNasabahByNIK() {
        System.out.print("Masukkan NIK Nasabah: ");
        String NIK = this.scan.nextLine();
        boolean isApproved = this.repository.approveNasabahByNIK(NIK);

        if (isApproved) {
            System.out.println("Nasabah dengan NIK " + NIK + " Berhasil Diapprove!");
        } else {
            System.out.println("Nasabah dengan NIK " + NIK + " Tidak Ditemukan!");
        }
    }

    private boolean tampilkanDataNasabah(List<Nasabah> nasabahList) {
        if (nasabahList.isEmpty()) {
            System.out.println("Tidak ada data nasabah yang bisa diapprove!");
            return false;
        }

        System.out.println();
        System.out.println(AsciiTable.getTable(
                nasabahList,
                Arrays.asList(
                        new Column().header("NIK")
                                .headerAlign(HorizontalAlign.CENTER)
                                .dataAlign(HorizontalAlign.LEFT)
                                .with(nasabah -> nasabah.getNIK()),
                        new Column().header("Nama")
                                .headerAlign(HorizontalAlign.CENTER)
                                .dataAlign(HorizontalAlign.LEFT).with(nasabah -> nasabah.getNama()),
                        new Column().header("Email")
                                .headerAlign(HorizontalAlign.CENTER)
                                .dataAlign(HorizontalAlign.LEFT).with(nasabah -> nasabah.getEmail()),
                        new Column().header("Password")
                                .headerAlign(HorizontalAlign.CENTER)
                                .dataAlign(HorizontalAlign.LEFT).with(nasabah -> nasabah.getPassword()),
                        new Column().header("Status")
                                .headerAlign(HorizontalAlign.CENTER)
                                .dataAlign(HorizontalAlign.LEFT)
                                .with(nasabah -> nasabah.getAccountStatus().toString()))));
        System.out.println();
        return true;
    }
}
