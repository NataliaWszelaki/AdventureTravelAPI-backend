package com.crud.adventuretravel.currencyapi.mapper;

import com.crud.adventuretravel.currencyapi.domain.status.CurrencyapiStatus;
import com.crud.adventuretravel.currencyapi.domain.status.CurrencyapiStatusDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CurrencyapiStatusMapper {

    public CurrencyapiStatus mapToStatus(CurrencyapiStatusDto currencyapiStatusDto) {

        return new CurrencyapiStatus(
                currencyapiStatusDto.getQuotas().getMonth().getTotal(),
                currencyapiStatusDto.getQuotas().getMonth().getUsed(),
                currencyapiStatusDto.getQuotas().getMonth().getRemaining(),
                LocalDate.now()
        );
    }
}
