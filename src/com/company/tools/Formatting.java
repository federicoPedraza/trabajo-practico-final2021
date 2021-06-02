package com.company.tools;

public class Formatting {
    public static String horizontalLine()
    {
        return "-------------------------------------------";
    }

    public static void drawHorizontalLine()
    {
        System.out.println(horizontalLine());
    }
    public static void clear() { System.out.print("\033[H\033[2J");
        System.out.flush();   }
}
