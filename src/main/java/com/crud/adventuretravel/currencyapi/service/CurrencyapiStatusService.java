package com.crud.adventuretravel.currencyapi.service;

import com.crud.adventuretravel.currencyapi.client.CurrencyapiClient;
import com.crud.adventuretravel.currencyapi.domain.status.CurrencyapiStatus;
import com.crud.adventuretravel.currencyapi.domain.status.CurrencyapiStatusDto;
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

        CurrencyapiStatusDto currencyapiStatusDto = currencyapiClient.fetchStatus();
        CurrencyapiStatus currencyapiStatus = currencyapiStatusMapper.mapToStatus(currencyapiStatusDto);
        currencyapiStatusRepository.save(currencyapiStatus);
        return currencyapiStatus;
    }
}
