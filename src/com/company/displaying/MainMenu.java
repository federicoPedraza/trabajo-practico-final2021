package com.company.displaying;

import com.company.models.Account;
import com.company.models.accounts.NormalAccount;
import com.company.tools.Formatting;
import com.company.tools.Validation;

import java.sql.SQLOutput;
import java.text.Format;
import java.text.Normalizer;
import java.util.Scanner;

public class MainMenu {
    public static Account loggedAccount;

    public static String logIn(String usernameOrEmail, String password)
    {
        loggedAccount = Account.checkCredentials(usernameOrEmail, password);
        if(loggedAccount == null) {
            ExecuteLogInMenu("Invalid credentials. Please try again.");
        }
        return "";
    }

    public static void ExecuteLogInMenu(String message)
    {
        Formatting.clear();
        Formatting.drawHorizontalLine();
        //Account.ListAllAccounts(); //debugging propourses
        if(message.length() > 0) System.out.println(message);
        System.out.println("Do you need a new account? Please type 'Sign up'");
        System.out.println("Please insert your username/email followed by the password.");
        Scanner scanner = new Scanner(System.in);
        String usernameOrEmail = scanner.next();
        if(usernameOrEmail.equals("Sign-up"))
        {
            ExecuteSignUpMenu();
        }
        String password = scanner.next();

        String logInStatus = logIn(usernameOrEmail,password);
        if(!logInStatus.equals("")) {
            System.out.println(logInStatus);
        }
    }

    //TODO: Finish sign-in up.
    public static void ExecuteSignUpMenu()
    {

        Account newAccount = new NormalAccount();

        Formatting.drawHorizontalLine();
        System.out.println("Please type the required information");
        System.out.println("This information can't be modified after confirming everything is ok.");
        System.out.println("1. First name");
        System.out.println("2. Last name");
        System.out.println("3. Phone number (optional but recommended, if you want to skip insert '-')");

        newAccount.setFirstName(Validation.scanForNumbers("Your first name can't contain any digit number or empty spaces.", false));
        newAccount.setLastName(Validation.scanForNumbers("Your last name can't contain any digit number or empty spaces.", false));

        newAccount.setCellNumber(Validation.scanForCharacters("Your phone number can't contain any type of characters, but numbers.", true));

        System.out.println(newAccount.getFirstName() + " " + newAccount.getLastName() + ". " + (newAccount.isVerified()? "Verified" : ""));
        if(!Validation.confirm())
        {
            Account.removeAccount(newAccount);
            ExecuteSignUpMenu();
            return;
        }
        System.out.println("The next information is required, but can be modified in the future.");
    }

    public static void Execute()
    {
        Formatting.clear();
        Formatting.drawHorizontalLine();
        System.out.println("Shopify!");
        if(loggedAccount == null) { ExecuteLogInMenu(""); }
        System.out.println(loggedAccount.getFirstName() + ", what can we do for you today?");
        Formatting.drawHorizontalLine();
        ExecuteMainMenu();
    }

    public static void ExecuteMainMenu()
    {
        Scanner scanner = new Scanner(System.in);

        DisplayMainMenu();
    }

    public static void DisplayMainMenu()
    {
        System.out.println("1. Buscar productos.");
        System.out.println("2. Publicar producto.");
        System.out.println("3. Acceder a tu perfil.");
        System.out.println("4. Salir de tu cuenta.");
    }
}
