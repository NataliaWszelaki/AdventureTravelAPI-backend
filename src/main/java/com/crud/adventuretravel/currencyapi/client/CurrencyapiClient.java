package com.crud.adventuretravel.currencyapi.client;

import com.crud.adventuretravel.currencyapi.config.CurrencyapiConfig;
import com.crud.adventuretravel.currencyapi.domain.rateexchange.RateExchangeApiResponseDto;
import com.crud.adventuretravel.currencyapi.domain.status.CurrencyapiStatusDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CurrencyapiClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyapiClient.class);
    private final RestTemplate restTemplate;
    private final CurrencyapiConfig currencyapiConfig;

    public CurrencyapiStatusDto fetchStatus() {

        URI url = UriComponentsBuilder
                .fromHttpUrl(currencyapiConfig.getCurrencyapiAPIEndpoint())
                .path("status")
                .queryParam("apikey", currencyapiConfig.getCurrencyapiAppKey())
                .build()
                .encode()
                .toUri();
        try {
            CurrencyapiStatusDto currencyapiStatusDto = restTemplate.getForObject(url, CurrencyapiStatusDto.class);
            return Optional.ofNullable(currencyapiStatusDto).orElseGet(() -> new CurrencyapiStatusDto());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new CurrencyapiStatusDto();
        }
    }

    public RateExchangeApiResponseDto fetchLatestExchangeRateEuroToPln() {

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
            RateExchangeApiResponseDto rateExchangeApiResponseDto = restTemplate.getForObject(url, RateExchangeApiResponseDto.class);
            return Optional.ofNullable(rateExchangeApiResponseDto).orElseGet(() -> new RateExchangeApiResponseDto());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new RateExchangeApiResponseDto();
        }

    }
}
