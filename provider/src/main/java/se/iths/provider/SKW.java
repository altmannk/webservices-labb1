package se.iths.provider;

import se.iths.service.CurrencyExchange;

public class SKW implements CurrencyExchange {

    @Override
    public double getCurrency(double amountSEK) {
        return amountSEK * 126.01;
    }

}
