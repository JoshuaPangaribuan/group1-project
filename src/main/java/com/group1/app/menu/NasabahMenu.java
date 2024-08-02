package com.group1.app.menu;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import com.group1.app.entity.Account;
import com.group1.app.entity.BankAccount;
import com.group1.app.entity.Nasabah;
import com.group1.app.entity.TransferHistory;
import com.group1.app.entity.enums.AccountRoles;
import com.group1.app.entity.enums.NasabahStatus;
import com.group1.app.entity.enums.TransferType;
import com.group1.app.repository.Repository;
import com.group1.common.exception.NoopException;
import com.group1.common.exception.NormalExitException;
import com.group1.common.exception.RequiredDependencyException;

public final class NasabahMenu implements Menu {
    private Scanner scan;
    private boolean runningState = true;
    private boolean optionState = true;
    private Repository nasabahRespository;

    public NasabahMenu(Scanner s, Repository repository) throws Exception {
        dependencyCheck(s, repository);

        this.scan = s;
        this.nasabahRespository = repository;
    }

    private void dependencyCheck(Scanner scan, Repository repo) throws RequiredDependencyException {
        if (scan == null) {
            throw new RequiredDependencyException("Scanner tidak boleh null!");
        }

        if (repo == null) {
            throw new RequiredDependencyException("Repository tidak boleh null!");
        }
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
                            simulasiRegistrasiNasabah();
                            break;

                        case 2:
                            optionState = false;
                            simulasiTransfer();
                            break;

                        case 3:
                            optionState = false;
                            checkHistoryTransaksi();
                            break;

                        case 4:
                            System.out.println("Feature Not Implemented Yet!");
                            break;

                        case 5:
                            optionState = false;
                            checkSaldo();
                            break;

                        case 6:
                            return new NoopException("no operation!");

                        default:
                            break;
                    }
                } catch (InputMismatchException im) {
                    System.out.println("Input yang anda masukkan salah!");
                    optionState = false;
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
        return new Exception("Unknown Error!");
    }

    private void display() {
        System.out.println("\nSelamat Datang di Simulasi Nasabah!");
        System.out.println("Silahkan pilih : ");
        System.out.println("1. Registrasi Nasabah Baru");
        System.out.println("2. Simulasi Transfer");
        System.out.println("3. Simulasi Cek History Transaksi");
        System.out.println("4. Tambah Rekening Bank");
        System.out.println("5. Cek Saldo");
        System.out.println("6. Kembali Ke Menu Awal");
        System.out.print("Pilihan anda : ");
    }

    private void simulasiRegistrasiNasabah() {
        System.out.print("\nMenu Registrasi Nasabah!\n");

        Nasabah nasabahBaru = new Nasabah();
        Account accountBaru = new Account();

        System.out.print("Masukkan NIK\t\t: ");
        nasabahBaru.setNIK(scan.nextLine());

        System.out.print("Masukkan Nama\t\t: ");
        nasabahBaru.setNama(scan.nextLine());

        System.out.print("Masukkan Email\t\t: ");
        accountBaru.setEmail(scan.nextLine());

        System.out.print("Masukkan Password\t: ");
        accountBaru.setPassword(scan.nextLine());

        System.out.println("\nSilahkan pilih bank yang akan anda daftarkan : ");
        List<String> bankList = nasabahRespository.getAllRegisteredBankLabel();

        for (String bank : bankList) {
            System.out.println((bankList.indexOf(bank) + 1) + ". " + bank);
        }

        System.out.print("Pilihan Bank\t\t: ");
        Integer bankOption = Integer.parseInt(scan.nextLine());
        String bankLabel = bankList.get(bankOption - 1);

        System.out.print("Masukkan Nomor Rekening\t: ");
        String accountNumber = scan.nextLine();
        if (!nasabahRespository.inquiryBankAccount(accountNumber, nasabahBaru.getNIK(), bankLabel)) {
            System.out.println("Nomor Rekening tidak ditemukan!");
            return;
        }

        nasabahBaru.setBankAccounts(Map.of(bankLabel, accountNumber));
        accountBaru.setRole(AccountRoles.NASABAH);
        nasabahBaru.setAccount(accountBaru);
        nasabahBaru.setAccountStatus(NasabahStatus.PENDING_ACTIVE);

        if (!nasabahRespository.saveDataNasabah(nasabahBaru)) {
            System.out.println("Registrasi Gagal!");
            return;
        }

        System.out.println("Registrasi Berhasil!");
        System.out.println("Silahkan login dengan email dan password yang telah anda daftarkan!");
    }

    private Nasabah loginChallenge() {
        System.out.print("\nMasukkan Email\t\t: ");
        String email = scan.nextLine();

        System.out.print("Masukkan Password\t: ");
        String password = scan.nextLine();

        Account account = new Account();
        account.setEmail(email);
        account.setPassword(password);
        account.setRole(AccountRoles.NASABAH);

        if (!nasabahRespository.validateAccount(account)) {
            return null;
        }

        Nasabah n = this.nasabahRespository.getNasabah(email, password);

        if (n.getAccountStatus() != NasabahStatus.ACTIVE) {
            System.out.println("Akun anda belum aktif atau akun tidak terdaftar!");
            return null;
        }

        return n;
    }

    private void simulasiTransfer() {
        Nasabah isLoginValid = loginChallenge();

        if ((isLoginValid == null)) {
            System.out.println("Email atau Password yang anda masukkan salah!");
            return;
        }

        List<String> bankList = nasabahRespository.getAllRegisteredBankLabel();
        System.out.println("Silahkan pilih bank yang akan anda transfer : ");
        for (String bank : bankList) {
            System.out.println((bankList.indexOf(bank) + 1) + ". " + bank);
        }

        System.out.print("Pilihan Bank\t\t: ");
        Integer toBankOption = Integer.parseInt(scan.nextLine());
        String toBankLabel = bankList.get(toBankOption - 1);

        System.out.print("Masukkan Nomor Rekening Tujuan\t: ");
        String toAccountNumber = scan.nextLine();

        System.out.print("Masukkan Nominal Transfer\t: ");
        Double nominal = Double.parseDouble(scan.nextLine());

        System.out.println("Silahkan pilih bank anda : ");
        for (String bank : isLoginValid.getBankAccounts().keySet()) {
            System.out.println((bankList.indexOf(bank) + 1) + ". " + bank);
        }

        System.out.print("Pilihan Bank\t\t: ");
        Integer fromBankOption = Integer.parseInt(scan.nextLine());
        String fromBankLabel = bankList.get(fromBankOption - 1);

        if (!nasabahRespository.inquiryBankAccount(isLoginValid.getBankAccounts().get(fromBankLabel), fromBankLabel)) {
            System.out.println("Nomor Rekening tidak ditemukan!");
            return;
        }

        BankAccount fromBankAccount = nasabahRespository.getBankAccount(
                isLoginValid.getBankAccounts().get(fromBankLabel),
                fromBankLabel);

        if (!nasabahRespository.inquiryBankAccount(toAccountNumber, toBankLabel)) {
            System.out.println("Nomor Rekening tujuan tidak ditemukan!");
            return;
        }

        BankAccount toBankAccount = nasabahRespository.getBankAccount(toAccountNumber, toBankLabel);

        if (nominal > nasabahRespository.getTransactionBalance(isLoginValid.getBankAccounts().get(fromBankLabel),
                fromBankLabel)) {
            System.out.println("Saldo anda tidak mencukupi!");
            return;
        }

        System.out.print("Masukkan PIN\t: ");
        String pin = scan.nextLine();

        if (!fromBankAccount.getPin().equals(pin)) {
            System.out.println("PIN yang anda masukkan salah!");
            return;
        }

        if (!nasabahRespository.deductAmount(isLoginValid.getBankAccounts().get(fromBankLabel), fromBankLabel,
                nominal)) {
            System.out.println("Transfer Gagal!");
            return;
        }

        if (!nasabahRespository.transferAmount(toAccountNumber, toBankLabel, nominal)) {
            System.out.println("Transfer Gagal!");
            return;
        }

        fromBankAccount.addTransferHistory(
                new TransferHistory(fromBankAccount.getAccountName(), fromBankLabel, toBankAccount.getAccountName(),
                        toBankLabel, nominal, TransferType.CASH_OUT));

        toBankAccount.addTransferHistory(
                new TransferHistory(fromBankAccount.getAccountName(), fromBankLabel, toBankAccount.getAccountName(),
                        toBankLabel, nominal, TransferType.CASH_IN));

        this.nasabahRespository.saveTransferHistory(List.of(
                new TransferHistory(fromBankAccount.getAccountName(), fromBankLabel, toBankAccount.getAccountName(),
                        toBankLabel, nominal, TransferType.CASH_OUT),
                new TransferHistory(fromBankAccount.getAccountName(), fromBankLabel, toBankAccount.getAccountName(),
                        toBankLabel, nominal, TransferType.CASH_IN)));

        System.out.println("\nTransfer Berhasil!");
    }

    private void checkSaldo() {
        Nasabah isLoginValid = loginChallenge();

        if ((isLoginValid == null)) {
            System.out.println("Email atau Password yang anda masukkan salah!");
            return;
        }

        // PERBAIKI
        // FOREACH PAKE MAP AJA
        List<String> bankList = nasabahRespository.getAllRegisteredBankLabel();
        System.out.println("\nSilahkan pilih bank anda : ");
        for (String bank : isLoginValid.getBankAccounts().keySet()) {
            System.out.println((bankList.indexOf(bank) + 1) + ". " + bank);
        }

        System.out.print("Pilihan Bank\t\t: ");
        Integer fromBankOption = Integer.parseInt(scan.nextLine());
        String fromBankLabel = bankList.get(fromBankOption - 1);

        if (!nasabahRespository.inquiryBankAccount(isLoginValid.getBankAccounts().get(fromBankLabel),
                fromBankLabel)) {
            System.out.println("\nNomor Rekening tidak ditemukan!");
            return;
        }

        System.out.println("\nSaldo anda adalah : Rp. "
                + nasabahRespository.getSaldo(isLoginValid.getBankAccounts().get(fromBankLabel), fromBankLabel));
    }

    private void checkHistoryTransaksi() {
        Nasabah isLoginValid = loginChallenge();

        if ((isLoginValid == null)) {
            System.out.println("Email atau Password yang anda masukkan salah!");
            return;
        }

        List<String> bankList = nasabahRespository.getAllRegisteredBankLabel();
        System.out.println("Silahkan pilih bank anda : ");
        for (String bank : isLoginValid.getBankAccounts().keySet()) {
            System.out.println((bankList.indexOf(bank) + 1) + ". " + bank);
        }

        System.out.print("Pilihan Bank\t\t: ");
        Integer fromBankOption = Integer.parseInt(scan.nextLine());
        String fromBankLabel = bankList.get(fromBankOption - 1);

        if (!nasabahRespository.inquiryBankAccount(isLoginValid.getBankAccounts().get(fromBankLabel),
                fromBankLabel)) {
            System.out.println("\nNomor Rekening tidak ditemukan!");
            return;
        }

        BankAccount bankAccount = nasabahRespository.getBankAccount(isLoginValid.getBankAccounts().get(fromBankLabel),
                fromBankLabel);

        System.out.println("\nHistory Transaksi : ");
        printHistoryTransaksi(bankAccount.getTransferHistories());
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
