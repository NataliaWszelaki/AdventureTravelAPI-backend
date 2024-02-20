package com.crud.adventuretravel.touristAttractionApi.mapper;

import com.crud.adventuretravel.touristAttractionApi.domain.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TouristAttractionApiLocationMapperTest {

    @InjectMocks
    private TouristAttractionApiLocationMapper touristAttractionApiLocationMapper;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    void shouldMapToLocationSearchResponse() throws JsonProcessingException {

        //Given
        String jsonString = "test";
        LocationSearchResponse locationSearchResponse = new LocationSearchResponse();
        when(objectMapper.readValue(jsonString, LocationSearchResponse.class)).thenReturn(locationSearchResponse);

        //When
        LocationSearchResponse mappedLocationSearchResponse = touristAttractionApiLocationMapper.mapToLocationSearchResponse(jsonString);

        //Then
        assertEquals(locationSearchResponse, mappedLocationSearchResponse);
    }

    @Test
    void shouldMapToLocationSearchDtoList() {

        //Given
        LocationSearchResponse locationSearchResponse = createLocationSearchResponse();

        //When
        List<LocationSearchDto> locationSearchDtoList = touristAttractionApiLocationMapper.mapToLocationSearchDtoList(locationSearchResponse);

        //Then
        assertEquals(2, locationSearchDtoList.size());
        assertEquals(12345, locationSearchDtoList.get(0).getLocation_id());
        assertEquals("Alicante", locationSearchDtoList.get(0).getName());
        assertEquals("Spain", locationSearchDtoList.get(0).getAncestor());
        assertEquals("Spain", locationSearchDtoList.get(0).getCountry());
        assertEquals("Calpe", locationSearchDtoList.get(1).getName());
        assertEquals("Province of Alicante", locationSearchDtoList.get(1).getAncestor());
        assertEquals("Spain", locationSearchDtoList.get(1).getCountry());
    }

    @Test
    void shouldReturnEmptyLocationSearchDtoList() {

        //Given
        List<LocationSearchData> locationSearchDataList = new ArrayList<>();
        LocationSearchResults locationSearchResults = new LocationSearchResults(locationSearchDataList);
        LocationSearchResponse locationSearchResponse = new LocationSearchResponse(locationSearchResults);

        //When
        List<LocationSearchDto> locationSearchDtoList = touristAttractionApiLocationMapper.mapToLocationSearchDtoList(locationSearchResponse);

        //Then
        assertEquals(0, locationSearchDtoList.size());
    }

    private LocationSearchResponse createLocationSearchResponse() {

        LocationSearchSubcategory locationSearchSubcategoryProvince = new LocationSearchSubcategory("province", "Province");
        LocationSearchSubcategory locationSearchSubcategoryCountry = new LocationSearchSubcategory("country", "Country");

        List<LocationSearchSubcategory> locationSearchSubcategoryListCountry = new ArrayList<>();
        locationSearchSubcategoryListCountry.add(locationSearchSubcategoryCountry);
        List<LocationSearchSubcategory> locationSearchSubcategoryListProvince = new ArrayList<>();
        locationSearchSubcategoryListProvince.add(locationSearchSubcategoryProvince);

        LocationSearchAncestor locationSearchAncestorCountry = new LocationSearchAncestor(locationSearchSubcategoryListCountry, "Spain");
        LocationSearchAncestor locationSearchAncestorProvinceAndCountry = new LocationSearchAncestor(locationSearchSubcategoryListProvince, "Province of Alicante");

        List<LocationSearchAncestor> locationSearchAncestorListAlicante = new ArrayList<>();
        locationSearchAncestorListAlicante.add(locationSearchAncestorCountry);
        List<LocationSearchAncestor> locationSearchAncestorListCalpe = new ArrayList<>();
        locationSearchAncestorListCalpe.add(locationSearchAncestorProvinceAndCountry);
        locationSearchAncestorListCalpe.add(locationSearchAncestorCountry);

        LocationSearchResultObject locationSearchResultObjectAlicante = new LocationSearchResultObject("12345", "Alicante", locationSearchAncestorListAlicante);
        LocationSearchResultObject locationSearchResultObjectCalpe = new LocationSearchResultObject("54321", "Calpe", locationSearchAncestorListCalpe);

        LocationSearchData locationSearchDataAlicante = new LocationSearchData(locationSearchResultObjectAlicante);
        LocationSearchData locationSearchDataCalpe = new LocationSearchData(locationSearchResultObjectCalpe);

        List<LocationSearchData> locationSearchDataList = new ArrayList<>();
        locationSearchDataList.add(locationSearchDataAlicante);
        locationSearchDataList.add(locationSearchDataCalpe);

        LocationSearchResults locationSearchResults = new LocationSearchResults(locationSearchDataList);

        return new LocationSearchResponse(locationSearchResults);
    }
}