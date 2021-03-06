package com.company.models;

import com.company.tools.Formatting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Account {
    public static int accountCount = 0;
    public static HashMap<Integer, Account> allAccounts = new HashMap<>();
    private Integer id;

    private String firstName;
    private String lastName;
    private Credentials credentials;

    private Cart cart;
    private final List<Receipt> receipts = new ArrayList<>();

    //region Constructors
    public Account() {
        credentials = new Credentials("", "", "", "");
        accountCount++;
        setId();
        receipts.clear();
        allAccounts.put(getId(), this);
    }

    public Account(String firstName, String lastName, String username, String email, String password, String cellNumber) {
        setFirstName(firstName);
        setLastName(lastName);
        credentials = new Credentials(username, password, email, cellNumber);

        accountCount++;
        setId();
        allAccounts.put(getId(), this);
        receipts.clear();
    }
    //endregion
    //region Getter and Setters

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String value) {
        firstName = value;
    }

    public void setLastName(String value) {
        lastName = value;
    }


    public void setId() {
        this.id = this.hashCode();
    }

    public Integer getId() {
        return id;
    }

    public Cart getCart() {
        if (this.cart == null) {
            setCart(new Cart(this));
        }

        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Credentials getCredentials() {
        if (credentials != null) {
            return credentials;
        }
        //creates an empty credential if there is none for some reason.
        else {
            return setCredentials("", "", "", "");
        }
    }

    public Credentials setCredentials(String username, String password, String email, String cellNumber) {
        credentials = new Credentials(username, password, email, cellNumber);
        return credentials;
    }

    //endregion
    //region Admin functions
    public static Account checkCredentials(String usernameOrEmail, String password) {
        for (Account account : allAccounts.values()) {
            if (account.credentials.verifyCredentials(usernameOrEmail, password)) {
                return account;
            }
        }

        return null;
    }

    public static boolean checkOneCredential(String input, String type) {
        for (Account account : allAccounts.values()) {
            switch (type) {
                case "username":
                    return account.getCredentials().getUsername().equals(input);
                case "email":
                    return account.getCredentials().getEmail().equals(input);
                case "cellnumber":
                    return account.getCredentials().getCellNumber().equals(input);
                default:
                    return false;
            }
        }

        return false;
    }

    public static void removeAccount(Account account) {
        if (existsAccount(account)) {
            accountCount--;
            allAccounts.remove(account.getId());
        }
    }

    public static boolean existsAccount(Account account) {
        return allAccounts.containsKey(account.getId());
    }
    //endregion

    public boolean isVerified() {
        if (credentials == null) {
            return false;
        }

        return credentials.getCellNumber() != null || credentials.getCellNumber() != "";
    }

    public int transitionsAmount() {
        return receipts.size();
    }

    public static void ListAllAccounts() {
        if (allAccounts.size() == 0) {
            System.out.println("No accounts found");
            return;
        }
        for (Account account : allAccounts.values()) {
            System.out.println(account);
        }
    }

    @Override
    public String toString() {
        return Formatting.horizontalLine() +
                "Cuenta " + (isVerified() ? "(verified)" : "") +
                ": " + getFirstName() + " '" + credentials.getUsername() + "' " +
                getLastName() + ".";
    }
}
