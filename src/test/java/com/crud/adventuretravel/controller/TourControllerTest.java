package com.crud.adventuretravel.controller;

import com.crud.adventuretravel.domain.TourDto;
import com.crud.adventuretravel.exception.TourNotFoundException;
import com.crud.adventuretravel.facade.TourFacade;
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
import java.util.List;

import static org.mockito.Mockito.*;

@SpringJUnitWebConfig
@WebMvcTest(TourController.class)
class TourControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TourFacade tourFacade;

    private TourDto tourDto;

    @BeforeEach
    void setUp() {

        tourDto = new TourDto(5L, "Costa Blanca in Spring", "Spain", "Sightseeing",
                LocalDate.of(2024, 4, 23), LocalDate.of(2024, 5, 8),
                "Alicante", "Alicante", 3000);
    }

    @Test
    void shouldFetchEmptyList() throws Exception {

        //Given
        when(tourFacade.getTours()).thenReturn(List.of());

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tours")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchTourList() throws Exception {

        //Given
        List<TourDto> tourDtoList = List.of(
                tourDto,
                new TourDto(8L, "German castles", "Germany", "Castles", LocalDate.of(2024, 5, 13),
                        LocalDate.of(2024, 5, 27), "Frankfurt am Main", "Frankfurt am Main", 1500));
        when(tourFacade.getTours()).thenReturn(tourDtoList);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tours")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Costa Blanca in Spring")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].country", Matchers.is("Spain")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description", Matchers.is("Sightseeing")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].startDate", Matchers.is("2024-05-13")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endDate", Matchers.is("2024-05-27")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].startLocation", Matchers.is("Frankfurt am Main")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endLocation", Matchers.is("Frankfurt am Main")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].priceEuro", Matchers.is(1500)));
    }

    @Test
    void shouldFetchTourById() throws Exception {

        //Given
        long tourId = 5L;

        when(tourFacade.getTourById(tourId)).thenReturn(tourDto);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tours/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Costa Blanca in Spring")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country", Matchers.is("Spain")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Sightseeing")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate", Matchers.is("2024-04-23")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate", Matchers.is("2024-05-08")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startLocation", Matchers.is("Alicante")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endLocation", Matchers.is("Alicante")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceEuro", Matchers.is(3000)));
    }

    @Test
    void shouldNotFetchTourById() throws Exception {

        //Given
        Long tourId = 223L;
        when(tourFacade.getTourById(tourId)).thenThrow(TourNotFoundException.class);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tours/223")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Tour with given ID doesn't exist."))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldCreateTour() throws Exception {

        //Given
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonSerializer<LocalDate>)
                        (localDate, type, jsonSerializationContext) ->
                                jsonSerializationContext.serialize(localDate.toString()))
                .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonDeserializer<LocalDate>)
                        (jsonElement, type, jsonDeserializationContext) ->
                                LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString()))
                .create();
        String jsonContent = gson.toJson(tourDto);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/tours")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldUpdateTour() throws Exception {

        //Given
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonSerializer<LocalDate>)
                        (localDate, type, jsonSerializationContext) ->
                                jsonSerializationContext.serialize(localDate.toString()))
                .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonDeserializer<LocalDate>)
                        (jsonElement, type, jsonDeserializationContext) ->
                                LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString()))
                .create();
        String jsonContent = gson.toJson(tourDto);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tours")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldUpdateTourDeactivate() throws Exception {

        //Given
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonSerializer<LocalDate>)
                        (localDate, type, jsonSerializationContext) ->
                                jsonSerializationContext.serialize(localDate.toString()))
                .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonDeserializer<LocalDate>)
                        (jsonElement, type, jsonDeserializationContext) ->
                                LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString()))
                .create();
        String jsonContent = gson.toJson(tourDto);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tours/deactivate")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldDeleteTour() throws Exception {

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tours/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
        verify(tourFacade, times(1)).deleteTour(1L);
    }
}