package com.crud.adventuretravel.touristAttractionApi.controller;

import com.crud.adventuretravel.touristAttractionApi.client.TouristAttractionAPIClient;
import com.crud.adventuretravel.touristAttractionApi.domain.AttractionDetailsDto;
import com.crud.adventuretravel.touristAttractionApi.domain.LocationSearchDto;
import com.crud.adventuretravel.touristAttractionApi.mapper.TouristAttractionApiMapper;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TouristAttractionApiController.class)
class TouristAttractionApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TouristAttractionAPIClient touristAttractionAPIClient;

    @MockBean
    private TouristAttractionApiMapper touristAttractionApiMapper;

    @Test
    void shouldFetchListOfLocationSearchDto() throws Exception {

        //Given
        String text = "alicante";
        String jsonString = "jsonString";
        List<LocationSearchDto> locationSearchDtoList = List.of(
                new LocationSearchDto(123, "Alicante", "Province of Alicante", "Spain"),
                new LocationSearchDto(125, "Calpe", "Province of Alicante", "Spain"));

        when(touristAttractionAPIClient.fetchLocationId(text)).thenReturn(jsonString);
        when(touristAttractionApiMapper.mapToLocationSearchDtoList(jsonString)).thenReturn(locationSearchDtoList);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tourist-attractions/location/" + text)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Alicante")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].country", Matchers.is("Spain")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].location_id", Matchers.is(125)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].ancestor", Matchers.is("Province of Alicante")));
    }

    @Test
    void shouldFetchEmptyListOfLocationSearchDto() throws Exception {

        //Given
        String text = "alicante";
        String jsonString = "jsonString";
        List<LocationSearchDto> locationSearchDtoList = new ArrayList<>();
        when(touristAttractionAPIClient.fetchLocationId(text)).thenReturn(jsonString);
        when(touristAttractionApiMapper.mapToLocationSearchDtoList(jsonString)).thenReturn(locationSearchDtoList);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tourist-attractions/location/" + text)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchListOfAttractionDetailsDto() throws Exception {

        //Given
        int locationId = 123;
        String jsonString = "jsonString";
        List<AttractionDetailsDto> attractionDetailsDtoList = List.of(
                new AttractionDetailsDto(123, "Alicante", "San Juan", "Beautiful place",
                        "Landmarks", "Private tour", 35),
                new AttractionDetailsDto(123, "Alicante", "Santa Barbara", "Beautiful castle",
                        "Castles", "Private tour", 135));
        when(touristAttractionAPIClient.fetchAttractionDetails(locationId)).thenReturn(jsonString);
        when(touristAttractionApiMapper.mapToAttractionsDetailsDtoList(jsonString)).thenReturn(attractionDetailsDtoList);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tourist-attractions/details/" + locationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("San Juan")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description", Matchers.is("Beautiful place")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].location_id", Matchers.is(123)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].category", Matchers.is("Castles")));
    }

    @Test
    void shouldFetchEmptyListOfAttractionDetailsDto() throws Exception {

        //Given
        int locationId = 123;
        String jsonString = "jsonString";
        List<AttractionDetailsDto> attractionDetailsDtoList = new ArrayList<>();
        when(touristAttractionAPIClient.fetchAttractionDetails(locationId)).thenReturn(jsonString);
        when(touristAttractionApiMapper.mapToAttractionsDetailsDtoList(jsonString)).thenReturn(attractionDetailsDtoList);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tourist-attractions/details/" + locationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }
}