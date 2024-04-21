module se.iths.consumer {
    requires se.iths.service;
    uses se.iths.service.CurrencyExchange;
    uses se.iths.service.annotation.Currency;
}