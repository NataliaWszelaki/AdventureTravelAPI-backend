package com.crud.adventuretravel.currencyapi.controller;

import com.crud.adventuretravel.currencyapi.domain.RateExchange;
import com.crud.adventuretravel.currencyapi.domain.RateExchangeDto;
import com.crud.adventuretravel.currencyapi.mapper.RateExchangeMapper;
import com.crud.adventuretravel.currencyapi.service.RateExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/rate_exchange")
@RequiredArgsConstructor
@CrossOrigin("*")
public class RateExchangeController {

    private final RateExchangeService rateExchangeService;
    private final RateExchangeMapper rateExchangeMapper;
    
    @GetMapping
    public ResponseEntity<RateExchangeDto> getRateExchange() {

        RateExchange rateExchange = rateExchangeService.getRateExchange();
        return ResponseEntity.ok(rateExchangeMapper. mapToRateExchangeDto(rateExchange));
    }
}