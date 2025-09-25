package org.example;

import org.example.ui.ConsoleUI;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        ConsoleUI ui = new ConsoleUI();
        ui.start();

    }
}