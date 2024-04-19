package se.iths.consumer;

import se.iths.service.CurrencyExchange;

import java.util.Scanner;
import java.util.ServiceLoader;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double amountSEK;
        int choiceMenu;

        while (true) {
            System.out.print("Enter amount in SEK: ");
            try {
                amountSEK = Double.parseDouble(scanner.nextLine());
                if (amountSEK < 0) {
                    System.out.println("Invalid input: " + amountSEK + ". Try again.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
                continue;
            }

            while (true) {
                printMenu();
                System.out.print("Choose an option: ");
                try {
                    choiceMenu = Integer.parseInt(scanner.nextLine());
                    if (choiceMenu < 1 || choiceMenu > 5) {
                        System.out.println("Invalid input: " + choiceMenu + ". Try again.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Try again.");
                    continue;
                }

                switch (choiceMenu) {
                    case 1 -> getDesiredCurrencyExchange("EUR", amountSEK);
                    case 2 -> getDesiredCurrencyExchange("SKW", amountSEK);
                    case 3 -> getAllCurrencyExchange(amountSEK);
                    case 4 -> {}
                    case 5 -> {
                        scanner.close();
                        System.exit(0);
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + choiceMenu);
                }

                if (choiceMenu == 4) break;

            }
        }

    }

    private static void printMenu() {
        System.out.println("""
                \nMenu:
                1. Euro (EUR)
                2. South Korean won (SKW)
                3. Show all currency
                4. Enter new SEK amount
                5. Exit
                """);
    }

    private static void getDesiredCurrencyExchange(String desiredCurrency, double amountSEK) {
        ServiceLoader<CurrencyExchange> loader = ServiceLoader.load(CurrencyExchange.class);
        for (CurrencyExchange currencyExchange : loader) {
            if (currencyExchange.getClass().getSimpleName().equals(desiredCurrency)) {
                System.out.printf("%s: %.1f\n", currencyExchange.getClass().getSimpleName(), currencyExchange.getCurrency(amountSEK));
                break;
            }
        }
    }

    private static void getAllCurrencyExchange(double amountSEK) {
        ServiceLoader<CurrencyExchange> loader = ServiceLoader.load(CurrencyExchange.class);
        for (CurrencyExchange currencyExchange : loader) {
            System.out.format("%s: %.1f\n", currencyExchange.getClass().getSimpleName(), currencyExchange.getCurrency(amountSEK));
        }
    }

}
