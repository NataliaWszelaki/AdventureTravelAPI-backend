package com.crud.adventuretravel.currencyapi.client;

import com.crud.adventuretravel.currencyapi.config.CurrencyapiConfig;
import com.crud.adventuretravel.currencyapi.domain.CurrencyapiStatusResponse;
import com.crud.adventuretravel.currencyapi.domain.RateExchangeApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrencyapiClient {

    private final RestTemplate restTemplate;
    private final CurrencyapiConfig currencyapiConfig;

    public CurrencyapiStatusResponse fetchStatus() {

        URI url = UriComponentsBuilder
                .fromHttpUrl(currencyapiConfig.getCurrencyapiAPIEndpoint())
                .path("status")
                .queryParam("apikey", currencyapiConfig.getCurrencyapiAppKey())
                .build()
                .encode()
                .toUri();
        try {
            CurrencyapiStatusResponse currencyapiStatusResponse = restTemplate.getForObject(url, CurrencyapiStatusResponse.class);
            return Optional.ofNullable(currencyapiStatusResponse).orElseGet(() -> new CurrencyapiStatusResponse());
        } catch (RestClientException e) {
            log.error("An error occurred: {}", e.getMessage(), e);
            return new CurrencyapiStatusResponse();
        }
    }

    public RateExchangeApiResponse fetchLatestExchangeRateEuroToPln() {

        URI url = UriComponentsBuilder
                .fromHttpUrl(currencyapiConfig.getCurrencyapiAPIEndpoint())
                .path("latest")
                .queryParam("apikey", currencyapiConfig.getCurrencyapiAppKey())
                .queryParam("currencies", "PLN")
                .queryParam("base_currency", "EUR")
                .build()
                .encode()
                .toUri();
        try {
            RateExchangeApiResponse rateExchangeApiResponse = restTemplate.getForObject(url, RateExchangeApiResponse.class);
            return Optional.ofNullable(rateExchangeApiResponse).orElseGet(() -> new RateExchangeApiResponse());
        } catch (RestClientException e) {
            log.error("An error occurred: {}", e.getMessage(), e);
            return new RateExchangeApiResponse();
        }
    }
}
