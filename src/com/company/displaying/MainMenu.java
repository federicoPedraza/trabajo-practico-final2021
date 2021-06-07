package com.company.displaying;

import com.company.models.Account;
import com.company.models.Publication;
import com.company.models.accounts.AdministratorAccount;
import com.company.models.accounts.NormalAccount;
import com.company.tools.ConvertingTool;
import com.company.tools.Formatting;
import com.company.tools.Validation;

import java.util.Scanner;

public class MainMenu {
    public static Account loggedAccount;

    public static String logIn(String usernameOrEmail, String password) {
        loggedAccount = Account.checkCredentials(usernameOrEmail, password);
        if (loggedAccount == null) {
            ExecuteLogInMenu("Invalid credentials. Please try again.");
        }
        return "";
    }

    public static void ExecuteLogInMenu(String message) {
        loggedAccount = null;
        Formatting.clear();
        Formatting.drawHorizontalLine();
        //Account.ListAllAccounts(); //debugging propourses
        if (message.length() > 0) System.out.println(message);
        System.out.println("Do you need a new account? Please type 'Sign-up'");
        System.out.println("Please insert your username/email followed by the password.");
        Scanner scanner = new Scanner(System.in);
        String usernameOrEmail = scanner.next();
        if (usernameOrEmail.equals("Sign-up")) {
            ExecuteSignUpMenu();
        }
        String password = scanner.next();

        String logInStatus = logIn(usernameOrEmail, password);
        if (!logInStatus.equals("")) {
            System.out.println(logInStatus);
        }
    }

    //TODO: Finish sign-in up.
    public static void ExecuteSignUpMenu() {

        Account newAccount = new NormalAccount();

        Formatting.drawHorizontalLine();
        System.out.println("Please type the required information");
        System.out.println("This information can't be modified after confirming everything is ok.");
        System.out.println("1. First name");
        System.out.println("2. Last name");
        System.out.println("3. Phone number (optional but recommended, if you want to skip insert '-')");

        newAccount.setFirstName(Validation.scanForNumbers("Your first name can't contain any digit number or empty spaces.", false));
        newAccount.setLastName(Validation.scanForNumbers("Your last name can't contain any digit number or empty spaces.", false));
        newAccount.getCredentials().setCellNumber(Validation.checkRegex("Your phone number can't contain any type of characters, but numbers.", "[0-9]+", true));

        System.out.println("The following information can be modified in a future.");
        System.out.println("1. Username");
        System.out.println("2. Password");
        System.out.println("3. Confirm password");
        System.out.println("4. Email");

        newAccount.getCredentials().setUsername(Validation.checkForStringLength("Your username must contain at least 4 characters, and must be available.", 4, 100, "username"));
        newAccount.getCredentials().setPassword(Validation.checkTwice("Your password doesn't match", Validation.checkForStringLength("Your password must contain at least 4 characters", 4, 100, "")));
        newAccount.getCredentials().setEmail(Validation.checkRegex("You must insert a valid email", "^(.+)@(.+)$", false));

        System.out.println(newAccount.getFirstName() + " " + newAccount.getLastName() + ". " + (newAccount.isVerified() ? "Verified" : ""));
        if (!Validation.confirm()) {
            Account.removeAccount(newAccount);
            ExecuteSignUpMenu();
            return;
        }
        System.out.println("Welcome to the community!");
        Start();
    }

    public static void Start() {
        Formatting.clear();
        Formatting.drawHorizontalLine();
        System.out.println("Shopify!");
        if (loggedAccount == null) {
            ExecuteLogInMenu("");
        }
        System.out.println(loggedAccount.getFirstName() + ", what can we do for you today?");
        Formatting.drawHorizontalLine();
        ExecuteMainMenu();

    }

    public static void ExecuteMainMenu() {
        //TODO: Case 0, 1, 2
        switch (ExecuteMenu(new String[]{
                "List me all products",
                "Search for a product",
                "Publish a product",
                "My profile (" + loggedAccount.getCredentials().getUsername() + ")",
                "Log off"})) {
            case 2: //Publish a product
                ExecutePublisherMenu();
                break;
            case 3: //My profile
                DisplayAccount(loggedAccount);
                ExecuteMainMenu();
                break;
            case 4: //Log off
                ExecuteLogInMenu("You've logged off! See u soon!");
                ExecuteMainMenu();
                break;
        }
    }

    public static void ExecutePublisherMenu() {
        int page = 0;
        int amount = 8;
        int selectedProduct = -1;
        do {
            switch (ExecuteMenu(ConvertingTool.categoriesToString(page, amount))) {
                case 1:
                    page--;
                    break;
                case 2:
                    selectedProduct = 2;
                    break;
                case 3:
                    selectedProduct = 3;
                    break;
                case 4:
                    selectedProduct = 4;
                    break;
                case 5:
                    selectedProduct = 5;
                    break;
                case 6:
                    selectedProduct = 6;
                    break;
                case 7:
                    selectedProduct = 7;
                    break;
                case 8:
                    selectedProduct = 8;
                    break;
                case 9:
                    selectedProduct = 9;
                case 0:
                    page++;
                    break;
                default:
                    selectedProduct = -1;
                    break;
            }
        } while ((selectedProduct < 0 || selectedProduct > 9) || !Publication.exists(selectedProduct * page));
        //TODO: Display publication by ID
        //DisplayPublication(Publication.searchById(selectedProduct * page));
    }

    public static void DisplayPublication(Publication publication) {

    }

    public static void DisplayAccount(Account account) {
        Formatting.drawHorizontalLine();
        System.out.println(account.getCredentials().getUsername());
        System.out.println(account.getFirstName() + " " + account.getLastName());
        System.out.println("(" + (account.isVerified() ? "Verified" : "Not verified") + ")");
        System.out.println(account instanceof AdministratorAccount ? "admin" : (account.transitionsAmount() > 5 ? "Trusthworthy" : "Newer"));
        System.out.println("E-mail: " + account.getCredentials().getEmail());
        System.out.println("Cell number: " + account.getCredentials().getCellNumber());
        System.out.println("ID: " + account.getId());
        Formatting.drawHorizontalLine();
        Formatting.pause();
    }

    public static int ExecuteMenu(String[] options) {
        Scanner scanner = new Scanner(System.in);
        int opt = -1;

        do {
            System.out.println("Choose an option:");
            for (int i = 0; i < options.length; i++) {
                System.out.println(i + ". " + options[i]);
            }
            opt = scanner.nextInt();
        } while (opt < 0 || opt > options.length);

        return opt;
    }
}
