package se.iths.provider;

import se.iths.service.CurrencyExchange;

public class EUR implements CurrencyExchange {

    @Override
    public double getCurrency(double amountSEK) {
        return amountSEK * 0.086;
    }

}
