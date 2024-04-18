package se.iths.provider;

import se.iths.service.CurrencyExchange;

public class EUR implements CurrencyExchange {

    @Override
    public String getCurrency(double amount) {
        return "EUR: " + String.format("%.2f", amount * 0.086);
    }

}
