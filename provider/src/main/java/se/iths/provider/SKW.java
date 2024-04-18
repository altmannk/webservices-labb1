package se.iths.provider;

import se.iths.service.CurrencyExchange;

public class SKW implements CurrencyExchange {

    @Override
    public String getCurrency(double amount) {
        return "SKW: " + String.format("%.2f", amount * 126.01);
    }

}
