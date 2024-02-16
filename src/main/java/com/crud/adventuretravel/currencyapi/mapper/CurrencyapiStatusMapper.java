package com.crud.adventuretravel.currencyapi.mapper;

import com.crud.adventuretravel.currencyapi.domain.CurrencyapiStatus;
import com.crud.adventuretravel.currencyapi.domain.CurrencyapiStatusResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CurrencyapiStatusMapper {

    public CurrencyapiStatus mapToStatus(CurrencyapiStatusResponse currencyapiStatusResponse) {

        return new CurrencyapiStatus(
                currencyapiStatusResponse.getQuotas().getMonth().getTotal(),
                currencyapiStatusResponse.getQuotas().getMonth().getUsed(),
                currencyapiStatusResponse.getQuotas().getMonth().getRemaining(),
                LocalDate.now()
        );
    }
}
