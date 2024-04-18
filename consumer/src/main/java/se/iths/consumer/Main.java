package se.iths.consumer;

import se.iths.service.CurrencyExchange;

import java.util.ServiceLoader;

public class Main {

    public static void main(String[] args) {
        ServiceLoader<CurrencyExchange> loader = ServiceLoader.load(CurrencyExchange.class);

        for (CurrencyExchange currencyExchange : loader) {
            System.out.println(currencyExchange.getCurrency(100));
        }

    }
}
