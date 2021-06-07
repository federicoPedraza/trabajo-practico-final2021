package com.company;

import com.company.databaseTesting.TestingDatabase;
import com.company.displaying.MenuDisplay;

public class Main {
    /*
        Shopify
        Integrantes => Federico Pedraza y Bruno Martin Pedraza
     */
    public static void main(String[] args) {
        TestingDatabase.Initializate();
        MenuDisplay.Start();
    }
}
