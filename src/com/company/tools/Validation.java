package com.company.tools;

import java.util.Scanner;

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

    public static boolean confirm()
    {
        return true;
    }
}
