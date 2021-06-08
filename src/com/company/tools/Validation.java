package com.company.tools;

import com.company.models.Account;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Validation {
    public static int seekNumber(String errorMessage, boolean skippeable)
    {
        Scanner scanner = new Scanner(System.in);
        int convertion = 0;
        while(true)
        {
            try {
                convertion = Integer.parseInt(scanner.next());
                break;
            } catch(NumberFormatException nfe) {
                System.out.println(errorMessage);
            }
        }

        return convertion;
    }

    public static String checkRegex(String errorMessage, String pattern, boolean skippeable) {
        Pattern regex = Pattern.compile(pattern);
        Scanner scanner = new Scanner(System.in);

        String lastScan = scanner.next();

        if (lastScan.equals("-") && skippeable) {
            return lastScan;
        }

        while (!lastScan.matches(regex.pattern())) {
            System.out.println(errorMessage);
            lastScan = scanner.next();
        }

        return lastScan;
    }

    public static String checkTwice(String errorMessage, String firstString) {
        Scanner scanner = new Scanner(System.in);
        String lastScan = "";

        boolean isCorrect = false;

        while (!isCorrect) {
            lastScan = scanner.next();
            if (lastScan.equals(firstString)) {
                isCorrect = true;
            } else {
                isCorrect = false;
                System.out.println(errorMessage);
            }
        }

        return lastScan;
    }

    public static String checkForStringLength(String errorMessage, int minimumLength, int maximunLength, String checkForDuplicatesType) {
        Scanner scanner = new Scanner(System.in);
        String lastScan = scanner.next();

        boolean isValid = false;

        while (!isValid) {
            System.out.println(errorMessage);
            lastScan = scanner.next();
            if (lastScan.length() >= minimumLength && lastScan.length() <= maximunLength || !Account.checkOneCredential(lastScan, checkForDuplicatesType))
                isValid = true;
        }

        return lastScan;
    }

    public static boolean confirm(String message, String expected)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        String opt = scanner.next();

        return opt.equals(expected);
    }
}
