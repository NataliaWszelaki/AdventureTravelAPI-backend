package com.crud.adventuretravel.currencyapi.service;

import com.crud.adventuretravel.currencyapi.client.CurrencyapiClient;
import com.crud.adventuretravel.currencyapi.domain.CurrencyapiStatus;
import com.crud.adventuretravel.currencyapi.domain.CurrencyapiStatusResponse;
import com.crud.adventuretravel.currencyapi.domain.Month;
import com.crud.adventuretravel.currencyapi.domain.Quotas;
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
        CurrencyapiStatusResponse statusDto = new CurrencyapiStatusResponse();
        Month month = new Month();
        month.setTotal(300);
        month.setUsed(5);
        month.setRemaining(295);
        Quotas quotas = new Quotas();
        quotas.setMonth(month);
        statusDto.setQuotas(quotas);
        when(currencyapiClient.fetchStatus()).thenReturn(statusDto);

        //When
        CurrencyapiStatus currencyapiStatus = currencyapiStatusService.fetchStatus();

        //Then
        verify(currencyapiClient, times(1)).fetchStatus();
        assertEquals(295, currencyapiStatus.getRemaining());
    }
}
