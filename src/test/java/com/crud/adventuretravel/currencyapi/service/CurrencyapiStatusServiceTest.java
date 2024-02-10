package com.crud.adventuretravel.currencyapi.service;

import com.crud.adventuretravel.currencyapi.client.CurrencyapiClient;
import com.crud.adventuretravel.currencyapi.domain.status.CurrencyapiStatus;
import com.crud.adventuretravel.currencyapi.domain.status.CurrencyapiStatusDto;
import com.crud.adventuretravel.currencyapi.domain.status.MonthDto;
import com.crud.adventuretravel.currencyapi.domain.status.QuotasDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CurrencyapiStatusServiceTest {

    @Autowired
    private CurrencyapiStatusService currencyapiStatusService;

    @MockBean
    private CurrencyapiClient currencyapiClient;

    @Test
    public void shouldFetchStatus() {

        //Given
        CurrencyapiStatusDto statusDto = new CurrencyapiStatusDto();
        MonthDto monthDto = new MonthDto();
        monthDto.setTotal(300);
        monthDto.setUsed(5);
        monthDto.setRemaining(295);
        QuotasDto quotasDto = new QuotasDto();
        quotasDto.setMonth(monthDto);
        statusDto.setQuotas(quotasDto);
        when(currencyapiClient.fetchStatus()).thenReturn(statusDto);

        //When
        CurrencyapiStatus currencyapiStatus = currencyapiStatusService.fetchStatus();

        //Then
        verify(currencyapiClient, times(1)).fetchStatus();
        assertEquals(295, currencyapiStatus.getRemaining());
    }
}
