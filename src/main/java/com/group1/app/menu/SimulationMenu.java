package com.group1.app.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.group1.app.menu.enums.MenuNavigation;
import com.group1.common.exception.NormalExitException;

public final class SimulationMenu implements Menu {
    private Scanner scan;
    private boolean runningState = true;
    private boolean optionState = true;
    private Map<MenuNavigation, Menu> menuList;

    public SimulationMenu(Scanner s) {
        this.scan = s;
        this.menuList = new HashMap<>();
    }

    public SimulationMenu(Scanner s, Map<MenuNavigation, Menu> menuList) {
        this.scan = s;
        this.menuList = menuList;
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
                        menuList.get(MenuNavigation.NASABAH_MENU).execute();
                        break;

                    case 2:
                        optionState = false;
                        System.out.println("Feature not ready yet!");
                        break;

                    case 3:
                        menuList.get(MenuNavigation.NASABAH_MENU).execute();
                        break;

                    case 4:
                        System.out.println("Exiting application...");
                        optionState = false;
                        runningState = false;
                        return new NormalExitException("Exiting simulation...");

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
            }
        }

        return new Exception("Unknown error!");
    }

    private void display() {
        System.out.println("\nSelamat Datang di Simulasi Brank Aggregator!");
        System.out.println("Silahkan Pilih Menu : ");
        System.out.println("1. Simulasi Aktor Bank");
        System.out.println("2. Simulasi Admin Aplikasi");
        System.out.println("3. Simulasi Nasabah");
        System.out.println("4. Exit simulasi");
        System.out.print("Pilihan anda : ");
    }

    public void setMenuList(Map<MenuNavigation, Menu> menuList) {
        this.menuList = menuList;
    }
}
