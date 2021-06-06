package com.company;

import com.company.databaseTesting.TestingDatabase;
import com.company.displaying.MainMenu;

public class Main {
    /*
        Shopify
        Integrantes => Federico Pedraza y Bruno Martin Pedraza
     */
    public static void main(String[] args) {
        TestingDatabase.Initializate();
        MainMenu.Start();
    }
}
