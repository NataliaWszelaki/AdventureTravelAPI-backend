package com.crud.adventuretravel.controller;

import com.crud.adventuretravel.domain.Attraction;
import com.crud.adventuretravel.domain.AttractionDto;
import com.crud.adventuretravel.exception.AttractionNotFoundException;
import com.crud.adventuretravel.mapper.AttractionMapper;
import com.crud.adventuretravel.service.AttractionDBService;
import com.google.gson.Gson;
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

import java.util.List;

import static org.mockito.Mockito.*;

@SpringJUnitWebConfig
@WebMvcTest(AttractionController.class)
class AttractionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AttractionDBService attractionDBService;

    @MockBean
    private AttractionMapper attractionMapper;

    private Attraction attraction;
    private AttractionDto attractionDto;

    @BeforeEach
    void setUp() {
        attraction = new Attraction(5L, "Making pasta", "Isola del Giglio",
                "How to make pasta", 30, 150);

        attractionDto = new AttractionDto(5L, "Making pasta", "Isola del Giglio",
                "How to make pasta", 30, 150);

    }

    @Test
    void shouldFetchEmptyList() throws Exception {

        //Given
        when(attractionDBService.getAllAttractions()).thenReturn(List.of());

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
        List<Attraction> attractionList = List.of(
                attraction,
                new Attraction(8L, "Wine tasting", "Sienna",
                        "tasting Wine in a beautiful restaurant",30, 150));
        List<AttractionDto> attractionDtoList = List.of(
                attractionDto,
                new AttractionDto(8L, "Wine tasting", "Sienna",
                        "tasting Wine in a beautiful restaurant",30, 150));

        when(attractionDBService.getAllAttractions()).thenReturn(attractionList);
        when(attractionMapper.mapToAttractionDtoList(attractionList)).thenReturn(attractionDtoList);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/attractions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Making pasta")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].city", Matchers.is("Isola del Giglio")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description", Matchers.is("tasting Wine in a beautiful restaurant")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].priceEuro", Matchers.is(30)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].pricePln", Matchers.is(150)));
    }

    @Test
    void shouldFetchAttractionById() throws Exception {

        //Given
        long attractionId = 5L;

        when(attractionDBService.getAttractionById(attractionId)).thenReturn(attraction);
        when(attractionMapper.mapToAttractionDto(attraction)).thenReturn(attractionDto);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/attractions/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Making pasta")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city", Matchers.is("Isola del Giglio")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("How to make pasta")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceEuro", Matchers.is(30)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pricePln", Matchers.is(150)));
    }

    @Test
    void shouldNotFetchAttractionById() throws Exception {

        //Given
        Long attractionId = 223L;
        when(attractionDBService.getAttractionById(attractionId)).thenThrow(AttractionNotFoundException.class);

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

        // Given
        when(attractionMapper.mapToAttraction(any(AttractionDto.class))).thenReturn(attraction);

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
        verify(attractionDBService, times(1)).createAttraction(attraction);
    }

    @Test
    void shouldUpdateAttraction() throws Exception {

        // Given
        when(attractionMapper.mapToAttraction(any(AttractionDto.class))).thenReturn(attraction);

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
        verify(attractionDBService, times(1)).updateAttraction(attraction);
    }

    @Test
    void shouldDeleteAttraction() throws Exception {

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/attractions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));

        verify(attractionDBService, times(1)).deleteAttraction(1L);
    }
}