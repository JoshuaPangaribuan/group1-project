package com.group1.app.menu;

import java.util.Map;
import java.util.Scanner;

import com.group1.app.menu.enums.MenuNavigation;
import com.group1.common.exception.NormalExitException;

public class BankMenu implements Menu {
    private Scanner scan;
    private boolean runningState = true;
    private boolean optionState = true;
    private Map<MenuNavigation, Menu> menuList;

    public BankMenu(Scanner s) {
        this.scan = s;
    }

    @Override
    public Exception execute() {

        while (runningState) {
            display();

            while (optionState) {
                try {
                    Integer option = scan.nextInt();
                    switch (option) {
                        case 1:
                            System.out.println("Feature Not Implemented Yet!");
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
            }
            if (!optionState) {
                break;
            }

            if (!optionState && runningState) {
                optionState = true;
                continue;
            }
        }
        return new Exception("Unknown error!");
    }

    public void setMenuList(Map<MenuNavigation, Menu> menuList) {
        this.menuList = menuList;
    }

    private void display() {
        System.out.println("\nSelamat Datang di Menu Simulasi Aktor Bank!");
        System.out.println("Silahkan pilih simulasi : ");
        System.out.println("1. Simulasi Registrasi Bank ke Bank Aggregator");
        System.out.println("2. Simulasi Cek Status Registrasi");
        System.out.println("3. Simulasi Cetak Detail Nasabah");
        System.out.println("4. etc");
        System.out.println("5. Kembali Ke Menu Awal");
        System.out.println("Pilihan anda : ");
    }
}
