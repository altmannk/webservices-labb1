package se.iths.consumer;

import se.iths.service.CurrencyExchange;

import java.util.Scanner;
import java.util.ServiceLoader;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Enter amount in SEK: ");
        double amountSEK = Double.parseDouble(scanner.nextLine());

        ServiceLoader<CurrencyExchange> loader = ServiceLoader.load(CurrencyExchange.class);

        for (CurrencyExchange currencyExchange : loader) {
            System.out.println(currencyExchange.getCurrency(amountSEK));
        }

    }
}
