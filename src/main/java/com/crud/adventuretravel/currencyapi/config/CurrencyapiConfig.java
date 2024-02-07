package com.crud.adventuretravel.currencyapi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CurrencyapiConfig {

    @Value("${currencyapi.api.endpoint.prod}")
    private String currencyapiAPIEndpoint;
    @Value("${currencyapi.app.key}")
    private String currencyapiAppKey;
}
