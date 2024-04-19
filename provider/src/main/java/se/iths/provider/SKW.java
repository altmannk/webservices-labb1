package se.iths.provider;

import se.iths.service.CurrencyExchange;

public class SKW implements CurrencyExchange {

    @Override
    public String getCurrency(double amountSEK) {
        return "SKW: " + String.format("%.2f", amountSEK * 126.01);
    }

}
