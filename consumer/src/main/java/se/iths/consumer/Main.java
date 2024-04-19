package se.iths.consumer;

import se.iths.service.CurrencyExchange;

import java.util.Scanner;
import java.util.ServiceLoader;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                \nWelcome!
                This program allows you to convert Swedish Kronor (SEK) to other currencies.
                Now lets get started.
                """);

        while (true) {
            double amountSEK = getAmountSEK(scanner);

            while (true) {
                printMenu();
                int choiceMenu = getChoiceMenu(scanner);

                switch (choiceMenu) {
                    case 1:
                        String desiredCurrency = userChoiceOfCurrency(scanner);
                        double desiredCurrencyExchange = getDesiredCurrencyExchange(desiredCurrency, amountSEK);
                        System.out.println(
                                "\n" + amountSEK + " SEK is " +
                                        String.format("%.1f", desiredCurrencyExchange) + " " + desiredCurrency);
                        break;
                    case 2:
                        System.out.println("\n" + amountSEK + " SEK in all available currencies: ");
                        getAllCurrencyExchange(amountSEK);
                        break;
                    case 3:
                        break;
                    case 0:
                        scanner.close();
                        System.exit(0);
                    default:
                        throw new IllegalStateException("Unexpected value: " + choiceMenu);
                }

                if (choiceMenu == 3) break;

            }
        }

    }

    private static void printMenu() {
        System.out.print("""
                \nDo you want to:
                1. Choose a specific currency
                2. Show SEK amount in all currency
                3. Enter new SEK amount
                0. Exit
                """);
    }

    private static double getAmountSEK(Scanner scanner) {
        double amountSEK;
        while (true) {
            System.out.print("Enter amount in SEK: ");
            try {
                amountSEK = Double.parseDouble(scanner.nextLine());
                if (amountSEK < 0) {
                    System.out.println("Invalid input: " + amountSEK + ". Try again.");
                    continue;
                }
                return amountSEK;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
            }
        }
    }

    private static int getChoiceMenu(Scanner scanner) {
        int choiceMenu;
        while (true) {
            System.out.print("Choose an option: ");
            try {
                choiceMenu = Integer.parseInt(scanner.nextLine());
                if (choiceMenu < 0 || choiceMenu > 3) {
                    System.out.println("Invalid input: " + choiceMenu + ". Try again.");
                    continue;
                }
                return choiceMenu;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
            }
        }
    }

    private static String userChoiceOfCurrency(Scanner scanner) {
        System.out.println("\nAll available currencies: ");
        showAllCurrencyNameAsAMenu();
        System.out.print("Enter desired currency: ");
        String desiredCurrency = scanner.nextLine().toUpperCase();

        if (!isValidCurrencyName(desiredCurrency)) {
            System.out.println("Invalid currency. Try again.");
            return userChoiceOfCurrency(scanner);
        }
        return desiredCurrency;
    }

    private static void showAllCurrencyNameAsAMenu() {
        ServiceLoader<CurrencyExchange> loader = ServiceLoader.load(CurrencyExchange.class);
        for (CurrencyExchange currencyExchange : loader) {
            System.out.println(currencyExchange.getClass().getSimpleName());
        }
    }

    private static boolean isValidCurrencyName(String desiredCurrency) {
        ServiceLoader<CurrencyExchange> loader = ServiceLoader.load(CurrencyExchange.class);
        for (CurrencyExchange currencyExchange : loader) {
            if (currencyExchange.getClass().getSimpleName().equals(desiredCurrency)) {
                return true;
            }
        }
        return false;
    }

    private static double getDesiredCurrencyExchange(String desiredCurrency, double amountSEK) {
        ServiceLoader<CurrencyExchange> loader = ServiceLoader.load(CurrencyExchange.class);
        for (CurrencyExchange currencyExchange : loader) {
            if (currencyExchange.getClass().getSimpleName().equals(desiredCurrency)) {
                return currencyExchange.getCurrency(amountSEK);
            }
        }
        throw new IllegalStateException("No currency exchange found for " + desiredCurrency);
    }

    private static void getAllCurrencyExchange(double amountSEK) {
        ServiceLoader<CurrencyExchange> loader = ServiceLoader.load(CurrencyExchange.class);
        for (CurrencyExchange currencyExchange : loader) {
            System.out.format("%s: %.1f\n",
                    currencyExchange.getClass().getSimpleName(),
                    currencyExchange.getCurrency(amountSEK)
            );
        }
    }

}
