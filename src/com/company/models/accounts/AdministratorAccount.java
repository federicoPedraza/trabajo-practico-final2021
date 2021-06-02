package com.company.models.accounts;

import com.company.models.Account;

public class AdministratorAccount extends Account {
    public AdministratorAccount(String firstName, String lastName, String username, String email, String password) {
        super(firstName, lastName, username, email, password);
    }
}
