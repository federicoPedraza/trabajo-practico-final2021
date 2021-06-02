package com.company.models;

import com.company.tools.Formatting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Account {
    //Static properties
    public static int accountCount = 0;
    public static HashMap<Integer, Account> allAccounts = new HashMap<>();

    private Integer id;

    private String firstName;
    private String lastName;
    private String cellNumber;
    private Credentials credentials;

    private Cart cart;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean setCellNumber(String value)
    {
        for(char character : value.toCharArray())
        {
            if(!Character.isDigit(character))
            {
                return false;
            }
        }
        this.cellNumber = value;
        return true;
    }

    public static void removeAccount(Account account)
    {
        if(existsAccount(account))
        {
            accountCount--;
            allAccounts.remove(account.getId());
        }
    }

    public static boolean existsAccount(Account account)
    {
        return allAccounts.containsKey(account.getId());
    }

    public boolean isVerified()
    {
        return cellNumber != null;
    }

    public Cart getCart() {
        if(this.cart == null)
        {
            setCart(new Cart(this));
        }

        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Account ()
    {
        accountCount++;
        setId();
        allAccounts.put(getId(), this);
    }

    public Account (String firstName, String lastName, String username, String email, String password)
    {
        accountCount++;

        this.firstName = firstName;
        this.lastName = lastName;
        credentials = new Credentials(username, password, email);
        setId();
        allAccounts.put(getId(), this);
    }

    public void setId()
    {
        this.id = this.hashCode();
    }

    public Integer getId()
    {
        return id;
    }

    public static Account checkCredentials(String usernameOrEmail, String password)
    {
        for(Account account : allAccounts.values())
        {
            if(account.credentials.verifyCredentials(usernameOrEmail, password))
            {
                return account;
            }
        }

        return null;
    }

    public static void ListAllAccounts()
    {
        if(allAccounts.size() == 0) {
            System.out.println("No accounts found");
            return;
        }
        for(Account account : allAccounts.values())
        {
            System.out.println(account);
        }
    }

    @Override
    public String toString()
    {
        return Formatting.horizontalLine() +
                "Cuenta " + (isVerified() ? "(verified)" : "") +
                ": " + getFirstName() + " '" + credentials.getUsername() + "' " +
                getLastName() + ".";
    }
}
