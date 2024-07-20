package com.group1.app;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

import com.group1.app.menu.BankMenu;
import com.group1.app.menu.Menu;
import com.group1.app.menu.NasabahMenu;
import com.group1.app.menu.SimulationMenu;
import com.group1.app.menu.enums.MenuNavigation;
import com.group1.app.repository.InMemoryRepository;
import com.group1.app.repository.Repository;
import com.group1.common.exception.NormalExitException;
import com.group1.common.exception.RequiredDependencyException;

public class BankAggregatorApp implements Application {
    private List<Closeable> closables = new ArrayList<>();

    @Override
    public void Start() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.Stop();
        }));

        try {
            blockingState(() -> {{
                        try {
                            // Initiate input from console
                            Scanner scan = new Scanner(System.in);
                            registerClosable(scan);

                            // Initiate repository
                            Repository repo = new InMemoryRepository();
                            registerClosable(repo);

                            SimulationMenu simulationMenu = new SimulationMenu(scan);
                            NasabahMenu nasabahMenu = new NasabahMenu(scan,repo);
                            BankMenu bankMenu = new BankMenu(scan);

                            Map<MenuNavigation, Menu> menuList = new HashMap<>();
                            menuList.put(MenuNavigation.SIMULATION_MENU, simulationMenu);
                            menuList.put(MenuNavigation.NASABAH_MENU, nasabahMenu);
                            menuList.put(MenuNavigation.BANK_MENU, bankMenu);

                            simulationMenu.setMenuList(menuList);
                            nasabahMenu.setMenuList(menuList);
                            bankMenu.setMenuList(menuList);

                            throw simulationMenu.execute();
                        }catch(RequiredDependencyException e){
                            System.out.println(e.getMessage());
                            return e;
                        } catch (Exception e) {
                            return e instanceof NormalExitException ? (NormalExitException) e : e;
                        }
                    }
                }
            );
            
        } catch (NormalExitException e) {
            System.out.println("\nNORMAL EXIT: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nABNORMAL EXIT:" + e.getMessage());
        } finally {
            Runtime.getRuntime().exit(1);
        }
    }

    @Override
    public void Stop() {
        System.out.println("Closing application...");

        for (Closeable closeable : closables) {
            try {
                closeable.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("All resources are stopped and flushed!");
        System.out.println("Thanks for using this simulation app!");
    }

    private void blockingState(Supplier<Exception> sup) throws Exception {
        throw sup.get();
    }

    private void registerClosable(Object closableCandidate) {
        this.closables.add((Closeable) closableCandidate);
    }

    public static BankAggregatorApp NewBankAggregatorApp() {
        BankAggregatorApp app = new BankAggregatorApp();
        return app;
    }
}
