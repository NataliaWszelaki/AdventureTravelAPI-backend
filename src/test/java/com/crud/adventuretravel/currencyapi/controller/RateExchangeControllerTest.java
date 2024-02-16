package com.crud.adventuretravel.currencyapi.controller;

import com.crud.adventuretravel.currencyapi.domain.RateExchange;
import com.crud.adventuretravel.currencyapi.domain.RateExchangeDto;
import com.crud.adventuretravel.currencyapi.mapper.RateExchangeMapper;
import com.crud.adventuretravel.currencyapi.service.RateExchangeService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(RateExchangeController.class)
class RateExchangeControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RateExchangeService rateExchangeService;

    @MockBean
    private RateExchangeMapper rateExchangeMapper;

    @Test
    void shouldFetchRateExchange() throws Exception {

        //Given
        RateExchange rateExchange = new RateExchange(1L, 4.432, LocalDate.of(2024,2,16));
        RateExchangeDto rateExchangeDto = new RateExchangeDto(1L, 4.432, LocalDate.of(2024,2,16));
        when(rateExchangeService.getRateExchange()).thenReturn(rateExchange);
        when(rateExchangeMapper.mapToRateExchangeDto(rateExchange)).thenReturn(rateExchangeDto);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/rate_exchange")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pln", Matchers.is(4.432)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rateExchangeDate", Matchers.is("2024-02-16")));
    }
}