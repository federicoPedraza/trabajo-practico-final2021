package com.company.models.accounts;

import com.company.models.Account;

public class NormalAccount extends Account {

    public NormalAccount() {
        super();
    }

    public NormalAccount(String firstName, String lastName, String username, String email, String password, String cellNumber) {
        super(firstName, lastName, username, email, password, cellNumber);
    }
}
