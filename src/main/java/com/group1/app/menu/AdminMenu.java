package com.group1.app.menu;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.group1.app.entity.Nasabah;
import com.group1.app.menu.enums.MenuNavigation;
import com.group1.app.repository.Repository;

final public class AdminMenu implements Menu {

    Scanner scan;
    private boolean runningState = true;
    private boolean optionState = true;
    private Map<MenuNavigation, Menu> menuList;
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
                        tampilkanDataNasabah();
                        break;

                    case 2:
                        // tampilkanDataBank();
                        break;

                    case 3:
                        // tampilkanDataTransaksi();
                        break;

                    case 4:
                        // tampilkanDataRekening();
                        break;

                    case 5:
                        optionState = false;
                        menuList.get(MenuNavigation.SIMULATION_MENU).execute();
                        break;

                    default:
                        System.out.println("Tolong masukkan input yang benar!");
                        optionState = false;
                        break;
                }
            }
        }

        return null;
    }

    private void display() {
        System.out.println("Menu Admin");
        System.out.println("1. Lihat Data Nasabah");
        System.out.println("2. Lihat Data Bank");
        System.out.println("3. Lihat Data Transaksi");
        System.out.println("4. Lihat Data Rekening");
        System.out.println("5. Kembali");
        System.out.print("Pilih Menu: ");
    }

    private void tampilkanDataNasabah() {
        List<Nasabah> nasabahList = repository.getAllDataNasabah();

        System.out.println(AsciiTable.getTable(
                nasabahList,
                Arrays.asList(
                        new Column().header("NIK").with(nasabah -> nasabah.getNIK()),
                        new Column().header("Nama").with(nasabah -> nasabah.getNama()),
                        new Column().header("Email").with(nasabah -> nasabah.getEmail()),
                        new Column().header("Password").with(nasabah -> nasabah.getPassword()))));
    }

    public void setMenuList(Map<MenuNavigation, Menu> menuList) {
        this.menuList = menuList;
    }
}
