package com.crud.adventuretravel.touristAttractionApi.mapper;

import com.crud.adventuretravel.touristAttractionApi.domain.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TouristAttractionApiMapperTest {

    @InjectMocks
    private TouristAttractionApiMapper touristAttractionApiMapper;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    void shouldMapToLocationSearchDtoList() throws IOException {

        //Given
        String jsonString = "test";

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
        LocationSearchResultObject locationSearchResultObjectCalpe = new LocationSearchResultObject("54321", "Calpe", locationSearchAncestorListCalpe);
        LocationSearchData locationSearchDataCalpe = new LocationSearchData(locationSearchResultObjectCalpe);
        LocationSearchResultObject locationSearchResultObjectAlicante = new LocationSearchResultObject("12345", "Alicante", locationSearchAncestorListAlicante);
        LocationSearchData locationSearchDataAlicante = new LocationSearchData(locationSearchResultObjectAlicante);
        List<LocationSearchData> locationSearchDataList = new ArrayList<>();
        locationSearchDataList.add(locationSearchDataAlicante);
        locationSearchDataList.add(locationSearchDataCalpe);
        LocationSearchResults locationSearchResults = new LocationSearchResults(locationSearchDataList);
        LocationSearchResponse locationSearchResponse = new LocationSearchResponse(locationSearchResults);

        when(objectMapper.readValue(jsonString, LocationSearchResponse.class)).thenReturn(locationSearchResponse);

        //When
        List<LocationSearchDto> locationSearchDtoList = touristAttractionApiMapper.mapToLocationSearchDtoList(jsonString);

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
    void shouldReturnEmptyLocationSearchDtoList() throws IOException {

        //Given
        String jsonString = "test";
        List<LocationSearchData> locationSearchDataList = new ArrayList<>();
        LocationSearchResults locationSearchResults = new LocationSearchResults(locationSearchDataList);
        LocationSearchResponse locationSearchResponse = new LocationSearchResponse(locationSearchResults);
        when(objectMapper.readValue(jsonString, LocationSearchResponse.class)).thenReturn(locationSearchResponse);

        //When
        List<LocationSearchDto> locationSearchDtoList = touristAttractionApiMapper.mapToLocationSearchDtoList(jsonString);

        //Then
        assertEquals(0, locationSearchDtoList.size());
    }

    @Test
    void shouldMapToAttractionsDetailsDtoList() throws IOException {

        //Given
        String jsonString = "test";

        AttractionDetailsSubcategory attractionDetailsSubcategorySanJuan = new AttractionDetailsSubcategory("47", "Sights & Landmarks");
        List<AttractionDetailsSubcategory> attractionDetailsSubcategoryListSanJuan = new ArrayList<>();
        attractionDetailsSubcategoryListSanJuan.add(attractionDetailsSubcategorySanJuan);

        AttractionDetailsOfferList attractionDetailsOfferListSanJuan = new AttractionDetailsOfferList("€70", "One-day trip");

        List<AttractionDetailsOfferList> attractionDetailsOfferListListSanJuan = new ArrayList<>();
        attractionDetailsOfferListListSanJuan.add(attractionDetailsOfferListSanJuan);
        AttractionDetailsOfferGroup attractionDetailsOfferGroupSanJuan = new AttractionDetailsOfferGroup(attractionDetailsOfferListListSanJuan);
        AttractionDetailsData attractionDetailsDataSanJuan = new AttractionDetailsData("111", "San Juan", "Nice place", "Alicante", attractionDetailsSubcategoryListSanJuan, attractionDetailsOfferGroupSanJuan);

        List<AttractionDetailsData> attractionDetailsDataList = new ArrayList<>();
        attractionDetailsDataList.add(attractionDetailsDataSanJuan);


        AttractionDetailsResults attractionDetailsResults = new AttractionDetailsResults(attractionDetailsDataList);
        AttractionDetailsResponse attractionDetailsResponse = new AttractionDetailsResponse(attractionDetailsResults);
        when(objectMapper.readValue(jsonString, AttractionDetailsResponse.class)).thenReturn(attractionDetailsResponse);

        //When
        List<AttractionDetailsDto> attractionDetailsDtoList = touristAttractionApiMapper.mapToAttractionsDetailsDtoList(jsonString);

        //Then
        assertEquals(1, attractionDetailsDtoList.size());
    }
}