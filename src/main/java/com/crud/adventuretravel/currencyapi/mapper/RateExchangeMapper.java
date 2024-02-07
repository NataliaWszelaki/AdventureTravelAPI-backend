package com.crud.adventuretravel.currencyapi.mapper;

import com.crud.adventuretravel.currencyapi.domain.rateexchange.RateExchange;
import com.crud.adventuretravel.currencyapi.domain.rateexchange.RateExchangeApiResponseDto;
import com.crud.adventuretravel.currencyapi.domain.rateexchange.RateExchangeDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RateExchangeMapper {

    public RateExchange mapToRateExchange(RateExchangeApiResponseDto rateExchangeApiResponseDto) {

        return new RateExchange(
                rateExchangeApiResponseDto.getData().getPln().getValue(),
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
