import se.iths.provider.EUR;
import se.iths.service.CurrencyExchange;

module se.iths.provider {
    requires se.iths.service;
    provides CurrencyExchange with EUR;
}