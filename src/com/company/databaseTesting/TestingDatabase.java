package com.company.databaseTesting;

import com.company.models.Account;
import com.company.models.Publication;
import com.company.models.accounts.AdministratorAccount;
import com.company.models.accounts.NormalAccount;
import com.company.models.categories.Category;

public class TestingDatabase {
    public static void Initializate() {
        Account federicoPedraza = new NormalAccount("Federico", "Pedraza", "federico.p", "federico.pedraza4@gmail.com", "3434", "2235395306");
        Account eugeniaIrigoyen = new NormalAccount("Eugenia", "Irigoyen", "eugeiri-2002", "eugeiri-2002@gmail.com", "willywonka", "223603402");
        Account superman = new AdministratorAccount("Bill", "Gates", "billy", "e-bilBoss@hotmail.com", "kittens", "32233451");

        Publication handSaw = new Publication("Hand of Steel", "Useful for cutting wood or similar materials", 33, federicoPedraza, 39.99, new Category[] { Category.CONSTRUCTION, Category.INDUSTRIESANDOFFICES });
        Publication redLipStick = new Publication("Red Beautier (Liquid)", "Red high quality lipstick.", 10, eugeniaIrigoyen, 14.99, new Category[]{ Category.BEAUTYANDHEALTH, Category.CLOTHINGANDACCESORIES });
        Publication strongRedLipStick = new Publication("Strong Red Beautier (Liquid)", "Strong red high quality lipstick", 2, eugeniaIrigoyen, 19.99, new Category[] { Category.BEAUTYANDHEALTH, Category.CLOTHINGANDACCESORIES});

    }
}
