package com.company;

import com.company.models.*;
import com.company.models.accounts.AdministratorAccount;
import com.company.models.accounts.NormalAccount;
import com.company.models.accounts.PlusAccount;
import com.company.models.categories.Category;
import com.company.tools.ConvertingTool;
import com.company.tools.Formatting;
import com.company.tools.Validation;

import java.text.Format;
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
        System.out.println("Username:");
        newAccount.getCredentials().setUsername(Validation.checkForStringLength("Your username must contain at least 4 characters, and must be available.", 4, 100, "username"));
        System.out.println("Password and confirmation:");
        newAccount.getCredentials().setPassword(Validation.checkTwiceIn("Your password doesn't match", Validation.checkForStringLength("Your password must contain at least 4 characters", 4, 100, ""), true));
        System.out.println("Email:");
        newAccount.getCredentials().setEmail(Validation.checkRegex("You must insert a valid email", "^(.+)@(.+)$", false));

        System.out.println(newAccount.getFirstName() + " " + newAccount.getLastName() + ". " + (newAccount.isVerified() ? "Verified" : ""));
        //interruption
        if (Validation.confirm("Do you confirm these changes? Type 'no' to go back", "no")) {
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
                (loggedAccount != null ? "Log off" : "")}, 1)) {
            case 1: //List me all products
                ExecutePublisherMenu("", 0,(loggedAccount == null ? 8 : loggedAccount.getPreferences().amountOfPublicationsPerPage));
                ExecuteMainMenu();
                break;
            case 2: //Search for a product
                break;

            case 3: //Publish a product or Log In
                if(loggedAccount == null) { ExecuteLogInMenu("You must log in to unlock this feature"); }
                ExecuteMainMenu();
                break;
            case 4: //My profile or Log In
                if(loggedAccount == null) { ExecuteLogInMenu(""); ExecuteMainMenu(); } else
                {
                    DisplayAccount(loggedAccount);
                    ExecuteMainMenu();
                }
                break;
            case 5: //Log off
                ExecuteLogInMenu("You've logged off! See u soon!");
                ExecuteMainMenu();
                break;
                default:
                    System.out.println("You must enter a valid option.");
                    ExecuteMainMenu();
                    break;
        }
    }

    /**Executes a search and display for all publications that matches the filterBy string
     * , depending on the page and the amounts of publications you want
     * to display.**/
    public static void ExecutePublisherMenu(String filterBy, int page, int amountPerPage)
    {
        System.out.println("Page: " + page);
        System.out.println("0. Next page");
        System.out.println("1. Back page");
        System.out.println("2. Main Menu");
        Formatting.drawHorizontalLine();
        var publications = Publication.allPublications.values().iterator();
        int counter = 3;
        while(publications.hasNext() && counter != amountPerPage)
        {
            if(loggedAccount != null)
            {
                if(!loggedAccount.getPreferences().compactBrowsing)
                    System.out.println(counter + ": " + publications.next().toString());
                else
                    System.out.println(counter + ": " + publications.next().getTitle());
            }
            else
                System.out.println(counter + ": " + publications.next().toString());
            counter++;
        }

        int opt = -1;
        do {
            opt = Validation.seekNumber("You must choose a valid option",false);
            switch(opt)
            {
                case 0:
                    ExecutePublisherMenu(filterBy, (page + 1), amountPerPage);
                case 1:
                    ExecutePublisherMenu(filterBy, (page == 0 ? page : page - 1), amountPerPage);
                case 2:
                    ExecuteMainMenu();
                default:
                    DisplayPublication(Publication.allPublications.get(opt - 3));
            }
        } while (opt < page * amountPerPage || opt > page * amountPerPage + amountPerPage + 3);
        Formatting.pause();
        ExecutePublisherMenu(filterBy, page, amountPerPage);
    }

    //TODO: Implement get publications by category
    public static void ExecutePublisherMenu(Category[] categories) {

    }

    //TODO: Fix cart adding multiple items, fix 0 comments not displaying any warning, fix extra information not available, fix go back sends you all the way back.
    //TODO: Make the stock responsive, add option to comment.
    public static void DisplayPublication(Publication publication) {
        Formatting.drawHorizontalLine();
        System.out.println(publication.getTitle());
        System.out.println("$" + publication.getCost());
        System.out.println(publication.getDescription());
        System.out.println("Stock: " + publication.getAvailableAmount());

        switch(ExecuteMenu(new String[] {
                (loggedAccount == null ? "Extra information (plus members only)" : "Extra information"),
                (loggedAccount == null ? "Add to cart (must log in)" : "Add to cart (" + loggedAccount.getCart().getAllProducts().length() + " items)"),
                "See comments (" + publication.comments.size() + ")",
                "Go back"
        }, 1))
        {
            case 0:
                if(loggedAccount instanceof PlusAccount)
                {
                    DisplayPublicationMoreInformation(publication);
                }
                else
                {
                    Formatting.displayPlusAccountWarn();

                }
                Formatting.pause();
                DisplayPublication(publication);
                break;
            case 1:
                if(loggedAccount == null)
                {
                    ExecuteLogInMenu("You must log in to add this item to your cart");
                }
                loggedAccount.getCart().add(publication);
                System.out.println("Thanks for adding this item to your cart!");
                Formatting.pause();
                DisplayPublication(publication);
                break;
            case 2:
                DisplayComments(publication);
                Formatting.pause();
                DisplayPublication(publication);
                break;
            default:
                break;
        }
    }

    public static void DisplayComments(Publication publication)
    {
        if(publication.comments.size() == 0) { System.out.println("This publication has no comments!"); return; };

        for(int i = 0; i < publication.comments.size(); i++)
        {
            System.out.println(i + ". (" + publication.comments.get(i).commentator.getCredentials().getUsername() + ")" + publication.comments.get(i).body
            + ((publication.comments.get(i).subComments.size() > 0) ? " | +" + publication.comments.get(i).subComments.size() + " comments" : " | "));
        }
    }

    public static void DisplayPublicationMoreInformation(Publication publication)
    {
        System.out.println(publication.getSeller().toString());
        System.out.println();
        Formatting.pause();
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

        if(loggedAccount.getId() == account.getId())
        {
            if(Validation.confirm("Type 'modify' to edit your profile or preferences.", "modify"))
            {
                ExecuteModifyAccountMenu("");
                ExecuteMainMenu();
            }
        }
        Formatting.pause();
        ExecuteMainMenu();
    }

    public static void ExecuteModifyAccountMenu(String message)
    {
        if(loggedAccount == null) return;
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        switch(ExecuteMenu(new String[] {
                "Change your username (" + loggedAccount.getCredentials().getUsername() + ")",
                "Change your password",
                "Change your cell number (" + loggedAccount.getCredentials().getCellNumber() + ")",
                "Compact browsing: " + (loggedAccount.getPreferences().compactBrowsing ? "Yes" : "No"),
                "Amount of publications per page: " + loggedAccount.getPreferences().amountOfPublicationsPerPage,
                "Delete account"
        }, 1))
        {
            case 1:
                System.out.println("Enter your new username: ");
                loggedAccount.getCredentials().setUsername(Validation.checkForStringLength("Your username must contain at least 4 characters, and must be available.", 4, 100, "username"));
                ExecuteModifyAccountMenu("Your username has been changed!");
                break;
            case 2:
                System.out.println("Enter your actual password (type '-' to exit): ");
                String lastScan = scanner.next();
                if(lastScan.equals("-")) { ExecuteModifyAccountMenu(""); }
                while(!Account.checkOneCredential(lastScan, "password"))
                {
                    System.out.println("Your password doesn't match (type '-' to exit)");
                    lastScan = scanner.next();
                    if(lastScan.equals("-")) { ExecuteModifyAccountMenu(""); }
                }
                System.out.println("Enter your new password (twice)");
                loggedAccount.getCredentials().setPassword(Validation.checkTwiceIn("Your password doesn't match", Validation.checkForStringLength("Your password must contain at least 4 characters", 4, 100, ""), true));
                ExecuteModifyAccountMenu("Your password has been changed!");
                break;
            case 3:
                System.out.println("Enter your new cell number, or type '-' to remove it (" + loggedAccount.getCredentials().getCellNumber() + ")");
                loggedAccount.getCredentials().setCellNumber(Validation.checkRegex("Your phone number can't contain any type of characters, but numbers.", "[0-9]+", true));
                ExecuteModifyAccountMenu("Your cellphone has been changed!");
                break;
            case 4:
                loggedAccount.getPreferences().compactBrowsing = !loggedAccount.getPreferences().compactBrowsing;
                ExecuteModifyAccountMenu("");
                break;
            case 5:
                loggedAccount.getPreferences().amountOfPublicationsPerPage *= 2;
                if(loggedAccount.getPreferences().amountOfPublicationsPerPage > AmountOfPublicationPerPageOptions.VERYLARGE.getCapacity())
                {
                    loggedAccount.getPreferences().amountOfPublicationsPerPage = 4;
                }
                ExecuteModifyAccountMenu("");
                break;
            case 6:
                //TODO: Delete account modification
                ExecuteModifyAccountMenu("This feature is been worked");
                break;
            default:
                DisplayAccount(loggedAccount);
                break;
        }
    }

    /**This is an interactive menu made of options given by code.
     * The user gets a list of options (String[]) and it returns the value given by the user.
     * The menu won't count empty options as values, and won't let the user **/
    public static int ExecuteMenu(String[] options, int indexOffset) {
        Scanner scanner = new Scanner(System.in);
        int opt = -1;
        int invalidOptions = Arrays.stream(options).filter(s -> !s.isEmpty() && !s.isBlank()).toArray().length;
        //remove all empty spaces https://stackoverflow.com/questions/1018750/how-to-convert-object-array-to-string-array-in-java
        options = Arrays.copyOf(Arrays.stream(options).filter(s -> !s.isEmpty() && !s.isBlank()).toArray(), invalidOptions, String[].class);
        do {
            System.out.println("Choose an option:");
            for (int i = indexOffset; i <= options.length; i++) {

                System.out.println(i + ". " + options[i - indexOffset]);
            }
            opt = Validation.seekNumber("You must choose a valid option",false);
        } while (opt < indexOffset || opt > options.length + indexOffset);

        return opt;
    }
}
