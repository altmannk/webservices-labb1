package se.iths.consumer;

import se.iths.service.CurrencyExchange;
import se.iths.service.annotation.Currency;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.ServiceLoader;

public class Main {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        Scanner scanner = new Scanner(System.in);

        System.out.println("""
                \nWelcome!
                This program allows you to convert
                Swedish Kronor (SEK) to other currencies.
                Now lets get started.
                """);

        while (true) {
            double amountSEK = promptForSEKAmount(scanner);

            while (true) {
                displayMenuOptions();
                int menuChoice = promptForMenuChoice(scanner);

                switch (menuChoice) {
                    case 1:
                        displayAvailableCurrencies();
                        String inputUser = promptForCurrencyChoice(scanner);
                        if (inputUser.equals("Q")) break;
                        displayOneCurrencyExchange(inputUser, amountSEK);
                        break;
                    case 2:
                        displayAllCurrencyExchange(amountSEK);
                        break;
                    case 3:
                        break;
                    case 0:
                        System.out.println("\nOkay, Bye!");
                        scanner.close();
                        System.exit(0);
                    default:
                        throw new IllegalStateException("Unexpected value: " + menuChoice);
                }
                if (menuChoice == 3) break;
            }
        }
    }

    private static void displayMenuOptions() {
        System.out.print("""
                \nDo you want to:
                1. Choose a specific currency
                2. Exchange SEK in all currencies
                3. Enter new SEK amount
                0. Exit
                """);
    }

    private static void displayAvailableCurrencies() {
        ServiceLoader<CurrencyExchange> loader = ServiceLoader.load(CurrencyExchange.class);
        System.out.println("\nAll available currencies: ");
        for (CurrencyExchange currencyExchange : loader) {
            System.out.format(
                    "• %s (%s)\n",
                    currencyExchange.getClass().getAnnotation(Currency.class).value(), //Currency Code
                    currencyExchange.getCurrencyName() //Currency Name
            );
        }
    }

    private static double promptForSEKAmount(Scanner scanner) {
        double amountSEK;

        while (true) {
            System.out.print("How much SEK do you want to convert: ");
            try {
                amountSEK = Double.parseDouble(scanner.nextLine().replace(",", "."));
                if (amountSEK < 0) {
                    System.out.println("Amount must be positive!");
                    continue;
                }
                return amountSEK;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Amount must be a number!");
            }
        }
    }

    private static int promptForMenuChoice(Scanner scanner) {
        int menuChoice;

        while (true) {
            System.out.print("Select menu option: ");
            try {
                menuChoice = Integer.parseInt(scanner.nextLine());
                if (menuChoice < 0 || menuChoice > 3) {
                    System.out.println("You need to choose a number from the menu.");
                    continue;
                }
                return menuChoice;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Menu option must be a number!");
            }
        }
    }

    private static String promptForCurrencyChoice(Scanner scanner) {
        String userInput;

        while (true) {
            System.out.print("\nChoose a currency code or 'Q' to get back to menu: ");
            try {
                userInput = scanner.nextLine().toUpperCase();
                if (userInput.equals("Q")) {
                    return "Q";
                } else if (!isValidCurrencyCode(userInput)) {
                    System.out.println("Invalid currency code. Try again!");
                    continue;
                }
                return userInput;
            } catch (Exception e) {
                System.out.println("Invalid input. Try again!");
            }
        }
    }

    private static boolean isValidCurrencyCode(String currencyCode) {
        return loadCurrencyConverter(currencyCode).stream().findFirst().isPresent();
    }

    private static void displayOneCurrencyExchange(String currencyCode, double amount) {
        for (CurrencyExchange converter : loadCurrencyConverter(currencyCode)) {
            System.out.format("\nSwedish Kronor to %s:\n%.1f SEK = %.1f %s\n",
                    converter.getCurrencyName(), //Currency Name
                    amount, //SEK
                    converter.getCurrencyAmount(amount), //Currency Amount
                    converter.getClass().getAnnotation(Currency.class).value() //Currency Code
            );
        }
    }

    private static List<CurrencyExchange> loadCurrencyConverter(String currencyCode) {
        ServiceLoader<CurrencyExchange> loader = ServiceLoader.load(CurrencyExchange.class);
        return loader.stream()
                .filter(c -> c.type().isAnnotationPresent(Currency.class) &&
                        c.type().getAnnotation(Currency.class).value().equals(currencyCode))
                .map(ServiceLoader.Provider::get)
                .toList();
    }

    private static void displayAllCurrencyExchange(double amount) {
        ServiceLoader<CurrencyExchange> loader = ServiceLoader.load(CurrencyExchange.class);
        System.out.println("\nSwedish Kronor: " + amount + " SEK in all currencies: ");
        for (CurrencyExchange currencyExchange : loader) {
            System.out.format("%s: %.1f %s\n",
                    currencyExchange.getCurrencyName(),
                    currencyExchange.getCurrencyAmount(amount),
                    currencyExchange.getClass().getAnnotation(Currency.class).value()
            );
        }
    }

}
