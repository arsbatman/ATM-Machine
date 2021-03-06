package Utility;
import java.util.Scanner;
import HP.MScreen;

public class MScanner {
    public static Scanner scan;

    public static void openScanner() {
        scan = new Scanner(System.in);
    }

    public static void closeScanner() {
        scan.close();
    }

    public static String getInput() {
        String input = scan.nextLine();

        if (input.toLowerCase().equals("home")) {
            MScreen.startup();
        }
        return input;
    }

    public static String getInputToLower() {
        String input = scan.nextLine().toLowerCase();

        if (input.equals("home")) {
            MScreen.startup();
        }
        return input;
    }

    public static int getNumber() {
        int input = scan.nextInt();

        scan.nextLine();

        return input;
    }
}