package com.group1.app.menu;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import com.group1.app.entity.Account;
import com.group1.app.entity.Nasabah;
import com.group1.app.entity.enums.AccountRoles;
import com.group1.app.entity.enums.NasabahStatus;
import com.group1.app.menu.enums.MenuNavigation;
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
        if (s == null || repository == null) {
            throw new RequiredDependencyException("Parameter tidak boleh null!");
        }

        this.scan = s;
        this.nasabahRespository = repository;
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
                            System.out.println("Feature Not Implemented Yet!");
                            break;

                        case 3:
                            System.out.println("Feature Not Implemented Yet!");
                            break;

                        case 4:
                            System.out.println("Feature Not Implemented Yet!");
                            break;

                        case 5:
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
        System.out.println("2. Simulasi Transfer Antar Bank");
        System.out.println("3. Simulasi Transfer Sesama Bank");
        System.out.println("4. Simulasi Transfer Beda Akun Bank");
        System.out.println("5. Kembali Ke Menu Awal");
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
}
