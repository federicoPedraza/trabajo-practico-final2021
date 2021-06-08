package com.company.tools;

public class Formatting {
    public static String horizontalLine() {
        return "-------------------------------------------";
    }

    public static void drawHorizontalLine() {
        System.out.println(horizontalLine());
    }

    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void pause() {
        System.out.println("Press any key to continue...");
        try {
            System.in.read();
        } catch (Exception e) {

        }
    }

    public static void space() {
        System.out.println("\n");
    }

    public static void displayPlusAccountWarn()
    {
        System.out.println("You must upgrade your account to a Plus Member. To do that, \n" +
                "go to your profile and select Plus membrecy");
    }
}
