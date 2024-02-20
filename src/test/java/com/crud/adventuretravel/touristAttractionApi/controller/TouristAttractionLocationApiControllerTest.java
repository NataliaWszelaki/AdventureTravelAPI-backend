package com.crud.adventuretravel.touristAttractionApi.controller;

import com.crud.adventuretravel.touristAttractionApi.client.TouristAttractionAPIClient;
import com.crud.adventuretravel.touristAttractionApi.domain.LocationSearchDto;
import com.crud.adventuretravel.touristAttractionApi.domain.LocationSearchResponse;
import com.crud.adventuretravel.touristAttractionApi.mapper.TouristAttractionApiLocationMapper;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TouristAttractionLocationApiController.class)
class TouristAttractionLocationApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TouristAttractionAPIClient touristAttractionAPIClient;

    @MockBean
    private TouristAttractionApiLocationMapper touristAttractionApiLocationMapper;

    @Test
    void shouldFetchListOfLocationSearchDto() throws Exception {

        //Given
        String text = "alicante";
        String jsonString = "jsonString";
        LocationSearchResponse locationSearchResponse = new LocationSearchResponse();
        List<LocationSearchDto> locationSearchDtoList = List.of(
                new LocationSearchDto(123, "Alicante", "Province of Alicante", "Spain"),
                new LocationSearchDto(125, "Calpe", "Province of Alicante", "Spain"));

        when(touristAttractionAPIClient.fetchLocationId(text)).thenReturn(jsonString);
        when(touristAttractionApiLocationMapper.mapToLocationSearchResponse(jsonString)).thenReturn(locationSearchResponse);
        when(touristAttractionApiLocationMapper.mapToLocationSearchDtoList(locationSearchResponse)).thenReturn(locationSearchDtoList);

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
        LocationSearchResponse locationSearchResponse = new LocationSearchResponse();
        List<LocationSearchDto> locationSearchDtoList = new ArrayList<>();
        when(touristAttractionAPIClient.fetchLocationId(text)).thenReturn(jsonString);
        when(touristAttractionApiLocationMapper.mapToLocationSearchResponse(jsonString)).thenReturn(locationSearchResponse);
        when(touristAttractionApiLocationMapper.mapToLocationSearchDtoList(locationSearchResponse)).thenReturn(locationSearchDtoList);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tourist-attractions/location/" + text)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }
}