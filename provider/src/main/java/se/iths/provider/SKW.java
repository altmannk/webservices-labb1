package se.iths.provider;

import se.iths.service.CurrencyExchange;
import se.iths.service.annotation.Currency;

@Currency("SKW")
public class SKW implements CurrencyExchange {

    @Override
    public double getCurrencyAmount(double amountSEK) {
        return amountSEK * 126.01;
    }

    @Override
    public String getCurrencyName() {
        return "South Korean won";
    }

}
