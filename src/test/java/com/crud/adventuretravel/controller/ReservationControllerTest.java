package com.crud.adventuretravel.controller;

import com.crud.adventuretravel.domain.*;
import com.crud.adventuretravel.facade.ReservationFacade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

@SpringJUnitWebConfig
@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationFacade reservationFacade;

    private ReservationDto reservationDto;

    @BeforeEach
    void setUp() {

        TourDto tourDto = new TourDto();
        tourDto.setId(3L);
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(342L);
        Set<Long> attractionLongSet = new HashSet<>();
        attractionLongSet.add(5L);
        attractionLongSet.add(8L);

        reservationDto = new ReservationDto(
                7L,
                tourDto.getId(),
                customerDto.getId(),
                attractionLongSet,
                LocalDate.of(2024, 2, 1),
                PaymentStatus.PENDING,
                ReservationStatus.PENDING);
    }

    @Test
    void shouldFetchEmptyList() throws Exception {

        //Given
        when(reservationFacade.getReservations()).thenReturn(List.of());

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/reservations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchReservationsList() throws Exception {

        //Given
        TourDto tourDto2 = new TourDto();
        tourDto2.setId(3L);
        CustomerDto customerDto2 = new CustomerDto();
        customerDto2.setId(342L);
        Set<Long> attractionLongSet2 = new HashSet<>();
        attractionLongSet2.add(5L);
        attractionLongSet2.add(8L);
        attractionLongSet2.add(17L);

        ReservationDto reservationDto2 = new ReservationDto(
                9L,
                tourDto2.getId(),
                customerDto2.getId(),
                attractionLongSet2,
                LocalDate.of(2024, 1, 11),
                PaymentStatus.CONFIRMED,
                ReservationStatus.CONFIRMED);
        List<ReservationDto> reservationDtoList = new ArrayList<>();
        reservationDtoList.add(reservationDto);
        reservationDtoList.add(reservationDto2);

        when(reservationFacade.getReservations()).thenReturn(reservationDtoList);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/reservations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(7)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tourId", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].customerId", Matchers.is(342)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].attractionLongSet", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].reservationDate", Matchers.is("2024-01-11")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].paymentStatus", Matchers.is("CONFIRMED")));
    }

    @Test
    void shouldFetchReservationById() throws Exception {

        //Given
        long reservationId = 7L;
        when(reservationFacade.getReservationById(reservationId)).thenReturn(reservationDto);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/reservations/7")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(7)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tourId", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerId", Matchers.is(342)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.attractionLongSet", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservationDate", Matchers.is("2024-02-01")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentStatus", Matchers.is("PENDING")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservationStatus", Matchers.is("PENDING")));
    }

    @Test
    void shouldCreateReservation() throws Exception {

        // Given
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonSerializer<LocalDate>)
                        (localDate, type, jsonSerializationContext) ->
                                jsonSerializationContext.serialize(localDate.toString()))
                .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonDeserializer<LocalDate>)
                        (jsonElement, type, jsonDeserializationContext) ->
                                LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString()))
                .create();
        String jsonContent = gson.toJson(reservationDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldUpdateReservation() throws Exception {

        // Given
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonSerializer<LocalDate>)
                        (localDate, type, jsonSerializationContext) ->
                                jsonSerializationContext.serialize(localDate.toString()))
                .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonDeserializer<LocalDate>)
                        (jsonElement, type, jsonDeserializationContext) ->
                                LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString()))
                .create();
        String jsonContent = gson.toJson(reservationDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldDeleteReservation() throws Exception {

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
        verify(reservationFacade, times(1)).deleteReservation(1L);
    }
}