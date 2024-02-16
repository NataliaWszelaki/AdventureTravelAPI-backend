package com.crud.adventuretravel.currencyapi.mapper;

import com.crud.adventuretravel.currencyapi.domain.RateExchange;
import com.crud.adventuretravel.currencyapi.domain.RateExchangeApiResponse;
import com.crud.adventuretravel.currencyapi.domain.RateExchangeDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RateExchangeMapper {

    public RateExchange mapToRateExchange(RateExchangeApiResponse rateExchangeApiResponse) {

        return new RateExchange(
                rateExchangeApiResponse.getData().getPln().getValue(),
                LocalDate.now()
        );
    }

    public RateExchangeDto mapToRateExchangeDto(RateExchange rateExchange) {

        return new RateExchangeDto(
                rateExchange.getId(),
                rateExchange.getPln(),
                rateExchange.getRateExchangeDate()
        );
    }
}
