package com.group1.app.menu;

public sealed interface Menu permits BankMenu, NasabahMenu, SimulationMenu {
    Exception execute();
}
