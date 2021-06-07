package com.company.displaying;

import com.company.models.Account;
import com.company.models.Publication;
import com.company.models.accounts.AdministratorAccount;
import com.company.models.accounts.NormalAccount;
import com.company.tools.ConvertingTool;
import com.company.tools.Formatting;
import com.company.tools.Validation;

import java.util.Arrays;
import java.util.Scanner;

public class MenuDisplay {
    public static Account loggedAccount;

    /**This method tries to vinculate an username and a password to an account, then it will assign "logged account" to it
     * Note that it returns a boolean. If the log was successful it will return true.**/
    public static boolean logIn(String usernameOrEmail, String password) {
        loggedAccount = Account.checkCredentials(usernameOrEmail, password);
        if (loggedAccount == null) {
            ExecuteLogInMenu("Invalid credentials. Please try again.");
        }
        return true;
    }

    /**This is an interactive visual interpretation of a log-in menu.
     * Once called, it will log off the user.
     * The "message" parameter refers to the custom message it will give
     * For example: "Invalid credentials" or "You've logged off"
     * If you don't want a custom message, leave it blank.**/
    public static void ExecuteLogInMenu(String message) {
        //It will log off
        loggedAccount = null;
        Formatting.clear();
        Formatting.drawHorizontalLine();
        //Account.ListAllAccounts(); //debugging propourses
        if (message.length() > 0) System.out.println(message);
        System.out.println("Do you need a new account? Please type 'Sign-up'");
        System.out.println("If you want to continue as a Guest, please type 'Continue'");
        System.out.println("Please insert your username/email followed by the password.");
        Scanner scanner = new Scanner(System.in);
        String usernameOrEmail = scanner.next();
        switch(usernameOrEmail)
        {
            case "Sign-up":
                ExecuteSignUpMenu();
                break;
            case "Continue":
                ExecuteMainMenu();
                break;
        }
        String password = scanner.next();

        //true: successful log
        boolean logInStatus = logIn(usernameOrEmail, password);
        if (!logInStatus) {
            System.out.println(logInStatus);
        }
    }

    /**This is an interactive visual interpretation of a sign-up menu.
     * It will add a new account to the system, and the user will fill it up step by step.
     * At the end, it will re-start the program with the new account.
     * If the user interrupts it, the account will be removed from the system.**/
    public static void ExecuteSignUpMenu() {

        Account newAccount = new NormalAccount();

        Formatting.drawHorizontalLine();
        System.out.println("Please type the required information");
        System.out.println("This information can't be modified after confirming everything is ok.");
        System.out.println("1. First name");
        System.out.println("2. Last name");
        System.out.println("3. Phone number (optional but recommended, if you want to skip insert '-')");

        newAccount.setFirstName(Validation.checkRegex("Your first name can't contain any digit number or empty spaces.","[a-zA-Z]+" ,false));
        newAccount.setLastName(Validation.checkRegex("Your last name can't contain any digit number or empty spaces.","[a-zA-Z]+" ,false));
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
        //interruption
        if (!Validation.confirm("Do you want to confirm?")) {
            Account.removeAccount(newAccount);
            System.out.println("You will be re-directed to the main menu");
            Formatting.pause();
            Start();
            return;
        }
        System.out.println("Welcome to the community!");
        Start();
    }

    /**This is the welcome page.
     * Everytime you want to start the program again, call this method.**/
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

    /**This is an interactive visual interpretation of a main menu.
     * The main menu contains a list of interactions depending on the user's account.
     * The method will loop itself unless a log-off occurs.
     * IF YOU HAVE AN ERROR, READ:
     * It is recommended to let options with account-dependency at last, so the menu can ignore it and there are no index error.**/
    public static void ExecuteMainMenu() {
        //TODO: Case 0, 1, 2
        /**This switch gets the input from the user within those given values**/
        switch (ExecuteMenu(new String[]{
                "List me all products",
                "Search for a product",
                "Publish a product" + (loggedAccount == null ? " (Must log in)" : ""),
                (loggedAccount != null) ? "My profile (" + loggedAccount.getCredentials().getUsername() + ")" : "Log in",
                (loggedAccount != null ? "Log off" : "")})) {
            case 2: //Publish a product or Log In
                if(loggedAccount == null) { ExecuteLogInMenu("You must log in to unlock this feature"); ExecuteMainMenu(); } else
                ExecutePublisherMenu();
                ExecuteMainMenu();
                break;
            case 3: //My profile or Log In
                if(loggedAccount == null) { ExecuteLogInMenu(""); ExecuteMainMenu(); } else
                {
                    DisplayAccount(loggedAccount);
                    ExecuteMainMenu();
                }
                break;
            case 4: //Log off
                ExecuteLogInMenu("You've logged off! See u soon!");
                ExecuteMainMenu();
                break;
                default:
                    System.out.println("You must enter a valid option.");
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

    /**This is an interactive menu made of options given by code.
     * The user gets a list of options (String[]) and it returns the value given by the user.
     * The menu won't count empty options as values, and won't let the user **/
    public static int ExecuteMenu(String[] options) {
        Scanner scanner = new Scanner(System.in);
        int opt = -1;
        int invalidOptions = Arrays.stream(options).filter(s -> !s.isEmpty() && !s.isBlank()).toArray().length;
        //remove all empty spaces https://stackoverflow.com/questions/1018750/how-to-convert-object-array-to-string-array-in-java
        options = Arrays.copyOf(Arrays.stream(options).filter(s -> !s.isEmpty() && !s.isBlank()).toArray(), invalidOptions, String[].class);
        do {
            System.out.println("Choose an option:");
            for (int i = 0; i < options.length; i++) {

                System.out.println(i + ". " + options[i]);
            }
            opt = Validation.seekNumber("You must choose a valid option",false);
        } while (opt < 0 || opt > options.length);

        return opt;
    }
}
