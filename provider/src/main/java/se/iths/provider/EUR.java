package se.iths.provider;

import se.iths.service.CurrencyExchange;
import se.iths.service.annotation.Currency;

@Currency("EUR")
public class EUR implements CurrencyExchange {

    @Override
    public double getCurrencyAmount(double amountSEK) {
        return amountSEK * 0.086;
    }

    @Override
    public String getCurrencyName() {
        return "Euro";
    }

}
