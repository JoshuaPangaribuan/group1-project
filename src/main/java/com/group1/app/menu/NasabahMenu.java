package com.group1.app.menu;

import java.util.Map;
import java.util.Scanner;

import com.group1.app.entity.Nasabah;
import com.group1.app.menu.enums.MenuNavigation;
import com.group1.app.repository.Repository;
import com.group1.common.exception.NormalExitException;

public final class NasabahMenu implements Menu {
    private Scanner scan;
    private boolean runningState = true;
    private boolean optionState = true;
    private Map<MenuNavigation, Menu> menuList;
    private Repository nasabahRespository; 

    public NasabahMenu(Scanner s) {
        this.scan = s;
    }

    @Override
    public Exception execute() {
        while (runningState) {
            display();

            while (optionState) {
                Integer option = scan.nextInt();

                try {
                    switch (option) {
                        case 1:
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
                            throw menuList.get(MenuNavigation.SIMULATION_MENU).execute();

                        default:
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
        return new Exception("Unknown Error!");
    }

    public void setMenuList(Map<MenuNavigation, Menu> menuList) {
        this.menuList = menuList;
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
        System.out.println("\nMenu Registrasi Nasabah!");

        Nasabah nasabahBaru = new Nasabah();
        System.out.print("Masukkan NIK : ");
        nasabahBaru.setNIK(scan.nextLine());

        System.out.print("Masukkan Nama : ");
        nasabahBaru.setNama(scan.nextLine());

        System.out.print("Masukkan Email : ");
        nasabahBaru.setEmail(scan.nextLine());

        System.out.print("Masukkan Password : ");
        nasabahBaru.setPassword(scan.nextLine());

        if (!nasabahRespository.saveDataNasabah(nasabahBaru)) {
            System.out.println("Registrasi Gagal!");
            return;
        }

        System.out.println("Registrasi Berhasil!");
        System.out.println("Silahkan login dengan email dan password yang telah anda daftarkan!");
    }
}
