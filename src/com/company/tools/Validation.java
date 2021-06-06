package com.company.tools;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Validation {
    public static String scanForNumbers(String errorMessage, boolean skippeable)
    {
        Scanner scanner = new Scanner(System.in);
        String lastScan = scanner.next();

        if(lastScan.equals("-") && skippeable) { return lastScan; }

        while(lastScan.matches(".*\\d.*") || lastScan.isEmpty() || lastScan == null)
        {
            System.out.println(errorMessage);
            lastScan = scanner.next();
        }

        return lastScan;
    }

    //TODO: Replace everything with checkRedex
    public static String checkRegex(String errorMessage, String pattern, boolean skippeable)
    {
        Pattern regex = Pattern.compile(pattern);
        Scanner scanner = new Scanner(System.in);

        String lastScan = scanner.next();

        if(lastScan.equals("-") && skippeable) { return lastScan; }

        while(!lastScan.matches(regex.pattern()))
        {
            System.out.println(errorMessage);
            lastScan = scanner.next();
        }

        return lastScan;
    }

    public static String checkTwice(String errorMessage, String firstString)
    {
        Scanner scanner = new Scanner(System.in);
        String lastScan = "";

        boolean isCorrect = false;

        while(!isCorrect)
        {
            lastScan = scanner.next();
            if(lastScan.equals(firstString))
            {
                isCorrect = true;
            }
            else
            {
                isCorrect = false;
                System.out.println(errorMessage);
            }
        }

        return lastScan;
    }

    public static String checkForStringLength(String errorMessage, int minimumLength, int maximunLength)
    {
        Scanner scanner = new Scanner(System.in);
        String lastScan = scanner.next();

        boolean isValid = false;

        while(!isValid)
        {
            System.out.println(errorMessage);
            lastScan = scanner.next();
            if(lastScan.length() >= minimumLength && lastScan.length() <= maximunLength)
                isValid = true;
        }

        return lastScan;
    }

    public static String scanForEmail(String errorMessage, boolean skippeable)
    {
        Scanner scanner = new Scanner(System.in);
        String lastScan = scanner.next();

        if(lastScan.equals("-") && skippeable) { return lastScan; }

        boolean isEmail = false;

        while(!isEmail)
        {
            if(lastScan.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"))
            {
                isEmail = true;
            }
            else
            {
                System.out.println(errorMessage);
                lastScan = scanner.next();
            }
        }

        return lastScan;
    }

    public static String scanForCharacters(String errorMessage, boolean skippeable)
    {
        Scanner scanner = new Scanner(System.in);
        String lastScan = scanner.next();

        if(lastScan.equals("-") && skippeable) { return lastScan; }

        boolean containsCharacter = true;

        while(containsCharacter)
        {
            try {
                Integer.parseInt(lastScan);
                containsCharacter = false;
            } catch (NumberFormatException e) {
                containsCharacter = true;
                System.out.println(errorMessage);
                lastScan = scanner.next();
            }
        }

        return lastScan;
    }

    //TODO: Finish-up confirm validation
    public static boolean confirm()
    {
        return true;
    }
}
