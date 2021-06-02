package com.company.databaseTesting;

import com.company.models.Account;
import com.company.models.accounts.AdministratorAccount;
import com.company.models.accounts.NormalAccount;

public class TestingDatabase {
    public static void Initializate()
    {
        Account federicoPedraza = new NormalAccount("Federico", "Pedraza", "federico.p", "federico.pedraza4@gmail.com", "3434");
        Account eugeniaIrigoyen = new NormalAccount("Eugenia", "Irigoyen", "eugeiri-2002", "eugeiri-2002@gmail.com", "willywonka");
        Account superman = new AdministratorAccount("Bill", "Gates", "billy", "e-bilBoss@hotmail.com", "kittens");
    }
}
