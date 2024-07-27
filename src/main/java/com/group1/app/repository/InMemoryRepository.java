package com.group1.app.repository;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.group1.app.entity.Account;
import com.group1.app.entity.Bank;
import com.group1.app.entity.BankAccount;
import com.group1.app.entity.Nasabah;
import com.group1.app.entity.enums.AccountRoles;
import com.group1.app.entity.enums.NasabahStatus;

public class InMemoryRepository implements Repository, Closeable {
    private List<Nasabah> nasabahList = new ArrayList<>();
    private List<Account> accountList = new ArrayList<>();
    private List<Bank> bankList = new ArrayList<>();

    public InMemoryRepository() {
        List<Account> list = List.of(
                new Account("admin@admin.com", "admin", AccountRoles.ADMIN),
                new Account("bri-admin@bri.com", "briadmin", AccountRoles.BANK),
                new Account("mandiri-admin@mandiri.com", "mandiriadmin", AccountRoles.BANK),
                new Account("bni-admin@bni.com", "bniadmin", AccountRoles.BANK),
                new Account("joshuapangaribuan@gmail.com", "joshua", AccountRoles.NASABAH),
                new Account("seseorang@gmail.com", "seseorang", AccountRoles.NASABAH));

        this.accountList.addAll(list);

        List<Bank> bList = List.of(
                new Bank("Bank Rakyat Indonesia", "BRI", List.of(
                        new BankAccount("338822957", "Joshua Ryandafres Pangaribuan", "250401", "11319029",
                                1_500_000.00, 50_000.00),
                        new BankAccount("338822861", "Seseorang", "250402", "11319030", 2_500_000.00, 50_000.00))),
                new Bank("Bank Mandiri", "MANDIRI", List.of(
                        new BankAccount("448822957", "Joshua Ryandafres Pangaribuan", "250401", "11319029",
                                3_500_000.00, 50_000.00))),
                new Bank("Bank Negara Indonesia", "BNI", List.of(
                        new BankAccount("558822957", "Indra", "889922", "11319031",
                                4_500_000.00, 50_000.00))));

        this.bankList.addAll(bList);

        List<Nasabah> nList = List.of(
                new Nasabah("11319029", "Joshua Ryandafres Pangaribuan", list.get(4), NasabahStatus.ACTIVE,
                        Map.of("BRI", "338822957", "MANDIRI", "448822957")),
                new Nasabah("11319030", "Seseorang", list.get(5), NasabahStatus.ACTIVE, Map.of("BRI", "338822861")));

        this.nasabahList.addAll(nList);
    }

    @Override
    public boolean saveDataNasabah(Nasabah n) {
        Nasabah nasabahExist = this.nasabahList.stream().filter(nasabah -> nasabah.getNIK().equals(n.getNIK()))
                .findFirst()
                .orElse(null);

        if (nasabahExist != null) {
            return false;
        }

        this.nasabahList.add(n);
        return this.accountList.add(n.getAccount());
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
    public Nasabah getNasabahByNIK(String NIK) {
        return this.nasabahList.stream().filter(n -> n.getNIK().equals(NIK)).findFirst().orElse(null);
    }

    @Override
    public Nasabah getNasabah(String email, String password) {
        Account account = new Account(email, password, AccountRoles.NASABAH);
        Nasabah nasabah = this.nasabahList.stream().filter(n -> n.getAccount().getEmail().equals(account.getEmail()))
                .findFirst().orElse(null);
        return nasabah;
    }

    @Override
    public List<String> getAllRegisteredBankLabel() {
        return this.bankList.stream().map(Bank::getLabel).toList();
    }

    @Override
    public boolean inquiryBankAccount(String accountNumber, String NIK, String bankLabel) {
        Bank bank = this.bankList.stream().filter(b -> b.getLabel().equals(bankLabel)).findFirst().orElse(null);
        if (bank != null) {
            BankAccount bankAccount = bank.getBankAccounts().stream()
                    .filter(ba -> ba.getAccountNumber().equals(accountNumber)).findFirst().orElse(null);
            if (bankAccount != null) {
                return bankAccount.getNIK().equals(NIK);
            }
        }
        return false;
    }

    @Override
    public boolean inquiryBankAccount(String accountNumber, String bankLabel) {
        Bank bank = this.bankList.stream().filter(b -> b.getLabel().equals(bankLabel)).findFirst().orElse(null);
        if (bank != null) {
            BankAccount bankAccount = bank.getBankAccounts().stream()
                    .filter(ba -> ba.getAccountNumber().equals(accountNumber)).findFirst().orElse(null);
            return bankAccount != null;
        }
        return false;
    }

    @Override
    public Double getTransactionBalance(String accountNumber, String bankLabel) {
        Bank bank = this.bankList.stream().filter(b -> b.getLabel().equals(bankLabel)).findFirst().orElse(null);
        if (bank != null) {
            BankAccount bankAccount = bank.getBankAccounts().stream()
                    .filter(ba -> ba.getAccountNumber().equals(accountNumber)).findFirst().orElse(null);
            if (bankAccount != null) {
                return bankAccount.getBalance() - bankAccount.getHoldBalance();
            }
        }
        return null;
    }

    @Override
    public Double getSaldo(String accountNumber, String bankLabel) {
        Bank bank = this.bankList.stream().filter(b -> b.getLabel().equals(bankLabel)).findFirst().orElse(null);
        if (bank != null) {
            BankAccount bankAccount = bank.getBankAccounts().stream()
                    .filter(ba -> ba.getAccountNumber().equals(accountNumber)).findFirst().orElse(null);
            if (bankAccount != null) {
                return bankAccount.getBalance();
            }
        }
        return null;
    }

    @Override
    public boolean deductAmount(String fromAccountNumber, String fromAccountLabel, Double amount) {
        Bank bank = this.bankList.stream().filter(b -> b.getLabel().equals(fromAccountLabel)).findFirst().orElse(null);
        if (bank != null) {
            BankAccount bankAccount = bank.getBankAccounts().stream()
                    .filter(ba -> ba.getAccountNumber().equals(fromAccountNumber)).findFirst().orElse(null);
            if (bankAccount != null) {
                if (bankAccount.getBalance() - bankAccount.getHoldBalance() >= amount) {
                    bankAccount.setBalance(bankAccount.getBalance() - amount);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean transferAmount(String toAccountNumber, String toAccountLabel, Double amount) {
        Bank bank = this.bankList.stream().filter(b -> b.getLabel().equals(toAccountLabel)).findFirst().orElse(null);
        if (bank != null) {
            BankAccount bankAccount = bank.getBankAccounts().stream()
                    .filter(ba -> ba.getAccountNumber().equals(toAccountNumber)).findFirst().orElse(null);
            if (bankAccount != null) {
                bankAccount.setBalance(bankAccount.getBalance() + amount);
                return true;
            }
        }
        return false;
    }

    @Override
    public BankAccount getBankAccount(String accountNumber, String bankLabel) {
        Bank bank = this.bankList.stream().filter(b -> b.getLabel().equals(bankLabel)).findFirst().orElse(null);
        if (bank != null) {
            return bank.getBankAccounts().stream().filter(ba -> ba.getAccountNumber().equals(accountNumber)).findFirst()
                    .orElse(null);
        }
        return null;
    }

    @Override
    public void close() throws IOException {
        nasabahList.clear();
    }
}
