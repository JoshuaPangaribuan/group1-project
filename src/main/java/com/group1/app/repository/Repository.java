package com.group1.app.repository;

import java.util.List;

import com.group1.app.entity.Account;
import com.group1.app.entity.BankAccount;
import com.group1.app.entity.Nasabah;
import com.group1.app.entity.enums.NasabahStatus;

public interface Repository {

    // NASABAH
    boolean saveDataNasabah(Nasabah n);

    List<Nasabah> getAllDataNasabah();

    List<Nasabah> getDataNasabahByAccountStatus(NasabahStatus status);

    boolean approveNasabahByNIK(String NIK);

    boolean approveAllPendingActiveNasabah();

    boolean validateAccount(Account account);

    Nasabah getNasabahByNIK(String NIK);

    Nasabah getNasabah(String email, String password);

    // BANK
    List<String> getAllRegisteredBankLabel();

    boolean inquiryBankAccount(String accountNumber, String NIK, String bankLabel);

    boolean inquiryBankAccount(String accountNumber, String bankLabel);

    Double getTransactionBalance(String accountNumber, String bankLabel);

    Double getSaldo(String accountNumber, String bankLabel);

    boolean deductAmount(String fromAccountNumber, String fromAccountLabel, Double amount);

    boolean transferAmount(String toAccountNumber, String toAccountLabel, Double amount);

    BankAccount getBankAccount(String accountNumber, String bankLabel);
}
