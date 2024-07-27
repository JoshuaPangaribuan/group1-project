package com.group1.app.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import com.group1.app.entity.Account;
import com.group1.app.entity.Bank;
import com.group1.app.entity.BankAccount;
import com.group1.app.entity.Nasabah;
import com.group1.app.entity.TransferHistory;
import com.group1.app.entity.enums.AccountRoles;
import com.group1.app.entity.enums.TransferType;
import com.group1.app.repository.Repository;
import com.group1.common.exception.NoopException;
import com.group1.common.exception.NormalExitException;

public final class BankMenu implements Menu {
    private Scanner scan;
    private boolean runningState = true;
    private boolean optionState = true;
    private Repository repository;

    public BankMenu(Scanner s, Repository repository) {
        this.scan = s;
        this.repository = repository;
    }

    @Override
    public Exception execute() {

        while (runningState) {
            display();

            while (optionState) {
                try {
                    Integer option = Integer.parseInt(this.scan.nextLine());

                    switch (option) {
                        case 1:
                            optionState = false;
                            cetakDetailNasabah();
                            break;

                        case 2:
                            optionState = false;
                            checkRiwayatTransaksi();
                            break;

                        case 4:
                            return new NoopException("no operation!");

                        default:
                            optionState = false;
                            System.out.println("Tolong masukkan input yang benar!");
                            break;
                    }
                } catch (Exception e) {
                    return e instanceof NormalExitException ? (NormalExitException) e : e;
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
        return new Exception("Unknown error!");
    }

    private void display() {
        System.out.println("\nSelamat Datang di Menu Simulasi Aktor Bank!");
        System.out.println("Silahkan pilih simulasi : ");
        System.out.println("1. Cetak Detail Nasabah");
        System.out.println("2. Cek Riwayat Transaksi");
        System.out.println("3. Cek Daftar Akun Bank");
        System.out.println("4. Kembali Ke Menu Awal");
        System.out.print("Pilihan anda : ");
    }

    private Bank loginChallenge() {
        System.out.print("Masukkan Email Bank\t: ");
        String email = this.scan.nextLine();

        System.out.print("Masukkan Password\t: ");
        String password = this.scan.nextLine();

        Account acc = new Account();
        acc.setEmail(email);
        acc.setPassword(password);
        acc.setRole(AccountRoles.BANK);

        if(!this.repository.validateAccount(acc)){
            return null;
        } else {
            return this.repository.getBankByEmail(email);
        }
    }

    private void cetakDetailNasabah(){
        Bank bank = loginChallenge();

        if(bank == null){
            System.out.println("Login gagal!");
            return;
        }

        System.out.print("\nMasukkan NIK\t\t: ");
        String nik = scan.nextLine();

        System.out.print("Masukkan No Rekening\t: ");
        String noRekening = scan.nextLine();

        System.out.println("\nDetail Nasabah : ");    
        Nasabah nasabah = this.repository.getNasabahByNIK(nik);
        BankAccount bankAccount = this.repository.getBankAccount(noRekening, bank.getLabel());

        if(nasabah == null || bankAccount == null){
            System.out.println("Data tidak ditemukan!");
            return;
        }

        System.out.println("Email Nasabah\t\t: " + nasabah.getAccount().getEmail());
        System.out.println("Nama Nasabah\t\t: " + nasabah.getNama());
        System.out.println("No Rekening\t\t: " + bankAccount.getAccountNumber());
        System.out.println("Saldo Nasabah\t\t: Rp. " + bankAccount.getBalance());

        System.out.println("\nTransaksi Terakhir : ");
        printHistoryTransaksi(bankAccount.getTransferHistories());
    }

    private void checkRiwayatTransaksi(){
        Bank bank = loginChallenge();

        if(bank == null){
            System.out.println("Login gagal!");
            return;
        }

        System.out.println("\nRiwayat Transaksi : ");
        List<TransferHistory> in = this.repository.getTransferHistoryInByBankLabel(bank.getLabel(), TransferType.CASH_IN);
        List<TransferHistory> out = this.repository.getTransferHistoryOutByBankLabel(bank.getLabel(), TransferType.CASH_OUT);

        List<TransferHistory> all = new ArrayList<>();
        all.addAll(in);
        all.addAll(out);

        printHistoryTransaksi(all);
    }

    private void printHistoryTransaksi(List<TransferHistory> transferHistories) {
        if (transferHistories.isEmpty()) {
            System.out.println("Tidak ada history transaksi!");
            return;
        }

        System.out.println();
        System.out.println(AsciiTable.getTable(
                transferHistories,
                Arrays.asList(
                        new Column().header("Dari")
                                .headerAlign(HorizontalAlign.CENTER)
                                .dataAlign(HorizontalAlign.LEFT)
                                .with(th -> th.getFromAccountName()),
                        new Column().header("Bank Label")
                                .headerAlign(HorizontalAlign.CENTER)
                                .dataAlign(HorizontalAlign.LEFT).with(th -> th.getFromBankLabel()),
                        new Column().header("Ke")
                                .headerAlign(HorizontalAlign.CENTER)
                                .dataAlign(HorizontalAlign.LEFT).with(th -> th.getToAccountName()),
                        new Column().header("Bank Label")
                                .headerAlign(HorizontalAlign.CENTER)
                                .dataAlign(HorizontalAlign.LEFT).with(th -> th.getToBankLabel()),
                        new Column().header("Nominal")
                                .headerAlign(HorizontalAlign.CENTER)
                                .dataAlign(HorizontalAlign.LEFT).with(th -> {
                                    TransferType type = th.getTransferType();
                                    return type == TransferType.CASH_IN ? "+ " + th.getAmount() : "- " + th.getAmount();
                                }))));
        System.out.println();
    }
}
