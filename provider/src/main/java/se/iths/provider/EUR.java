package se.iths.provider;

import se.iths.service.CurrencyExchange;

public class EUR implements CurrencyExchange {

    @Override
    public String getCurrency(double amountSEK) {
        return "EUR: " + String.format("%.2f", amountSEK * 0.086);
    }

}
