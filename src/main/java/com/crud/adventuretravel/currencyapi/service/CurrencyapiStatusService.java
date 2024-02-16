package com.crud.adventuretravel.currencyapi.service;

import com.crud.adventuretravel.currencyapi.client.CurrencyapiClient;
import com.crud.adventuretravel.currencyapi.domain.CurrencyapiStatus;
import com.crud.adventuretravel.currencyapi.domain.CurrencyapiStatusResponse;
import com.crud.adventuretravel.currencyapi.mapper.CurrencyapiStatusMapper;
import com.crud.adventuretravel.currencyapi.repository.CurrencyapiStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyapiStatusService {

    private final CurrencyapiClient currencyapiClient;
    private final CurrencyapiStatusMapper currencyapiStatusMapper;
    private final CurrencyapiStatusRepository currencyapiStatusRepository;


    public CurrencyapiStatus fetchStatus() {

        CurrencyapiStatusResponse currencyapiStatusResponse = currencyapiClient.fetchStatus();
        CurrencyapiStatus currencyapiStatus = currencyapiStatusMapper.mapToStatus(currencyapiStatusResponse);
        currencyapiStatusRepository.save(currencyapiStatus);
        return currencyapiStatus;
    }
}
