package com.crud.adventuretravel.controller;

import com.crud.adventuretravel.domain.AttractionDto;
import com.crud.adventuretravel.exception.AttractionNotFoundException;
import com.crud.adventuretravel.facade.AttractionFacade;
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
@WebMvcTest(AttractionController.class)
class AttractionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AttractionFacade attractionFacade;

    private AttractionDto attractionDto;

    @BeforeEach
    void setUp() {

        attractionDto = new AttractionDto(5L, 123, "Isola del Giglio", "Isola del Giglio", "Making pasta",
                "Cooking", "How to make pasta", 30);
    }

    @Test
    void shouldFetchEmptyList() throws Exception {

        //Given
        when(attractionFacade.getAttractions()).thenReturn(List.of());

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/attractions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchAttractionList() throws Exception {

        //Given
        List<AttractionDto> attractionDtoList = List.of(
                attractionDto,
                new AttractionDto(8L, 234, "Sienna", "Wine tasting", "Vineyard",
                        "Tasting Wine in a beautiful restaurant", "Private tour", 30));
        when(attractionFacade.getAttractions()).thenReturn(attractionDtoList);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/attractions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Isola del Giglio")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].city", Matchers.is("Isola del Giglio")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description", Matchers.is("Vineyard")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].priceEuro", Matchers.is(30)));
    }

    @Test
    void shouldFetchAttractionById() throws Exception {

        //Given
        long attractionId = 5L;
        when(attractionFacade.getAttractionById(attractionId)).thenReturn(attractionDto);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/attractions/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Isola del Giglio")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city", Matchers.is("Isola del Giglio")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Making pasta")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceEuro", Matchers.is(30)));
    }

    @Test
    void shouldNotFetchAttractionById() throws Exception {

        //Given
        Long attractionId = 223L;
        when(attractionFacade.getAttractionById(attractionId)).thenThrow(AttractionNotFoundException.class);

        //When & Then
        mockMvc
                 .perform(MockMvcRequestBuilders
                        .get("/v1/attractions/223")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Attraction with given ID doesn't exist."))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldCreateAttraction() throws Exception {

        //Given
        Gson gson = new Gson();
        String jsonContent = gson.toJson(attractionDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/attractions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldUpdateAttraction() throws Exception {

        //Given
        Gson gson = new Gson();
        String jsonContent = gson.toJson(attractionDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/attractions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldUpdateAttractionDeactivate() throws Exception {

        // Given
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonSerializer<LocalDate>)
                        (localDate, type, jsonSerializationContext) ->
                                jsonSerializationContext.serialize(localDate.toString()))
                .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonDeserializer<LocalDate>)
                        (jsonElement, type, jsonDeserializationContext) ->
                                LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString()))
                .create();
        String jsonContent = gson.toJson(attractionDto);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/attractions/deactivate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldDeleteAttraction() throws Exception {

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/attractions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
        verify(attractionFacade, times(1)).deleteAttraction(1L);
    }
}