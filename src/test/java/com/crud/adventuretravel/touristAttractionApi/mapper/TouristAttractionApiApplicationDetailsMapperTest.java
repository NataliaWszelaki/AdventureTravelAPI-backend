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
class TouristAttractionApiApplicationDetailsMapperTest {

    @InjectMocks
    private TouristAttractionApiAttractionDetailsMapper touristAttractionApiAttractionDetailsMapper;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    void shouldMapToAttractionDetailsResponse() throws JsonProcessingException {

        //Given
        String jsonString = "test";
        AttractionDetailsResponse attractionDetailsResponse = new AttractionDetailsResponse();
        when(objectMapper.readValue(jsonString, AttractionDetailsResponse.class)).thenReturn(attractionDetailsResponse);

        //When
        AttractionDetailsResponse mappedAttractionDetailsResponse = touristAttractionApiAttractionDetailsMapper.mapToAttractiondetailsResponse(jsonString);

        //Then
        assertEquals(attractionDetailsResponse, mappedAttractionDetailsResponse);
    }

    @Test
    void shouldMapToAttractionsDetailsDtoList() {

        //Given
        AttractionDetailsResponse attractionDetailsResponse = createAttractionDetailsResponse();

        //When
        List<AttractionDetailsDto> attractionDetailsDtoList = touristAttractionApiAttractionDetailsMapper.mapToAttractionsDetailsDtoList(attractionDetailsResponse);

        //Then
        assertEquals(1, attractionDetailsDtoList.size());
    }

    @Test
    void shouldReturnEmptyAttractionsDetailsDtoList() {

        //Given
        List<AttractionDetailsData> attractionDetailsDataList = new ArrayList<>();
        AttractionDetailsResults attractionDetailsResults = new AttractionDetailsResults(attractionDetailsDataList);
        AttractionDetailsResponse attractionDetailsResponse = new AttractionDetailsResponse(attractionDetailsResults);

        //When
        List<AttractionDetailsDto> attractionDetailsDtoList = touristAttractionApiAttractionDetailsMapper.mapToAttractionsDetailsDtoList(attractionDetailsResponse);

        //Then
        assertEquals(0, attractionDetailsDtoList.size());

    }


    private AttractionDetailsResponse createAttractionDetailsResponse() {

        AttractionDetailsSubcategory attractionDetailsSubcategorySanJuan = new AttractionDetailsSubcategory("47", "Sights & Landmarks");

        List<AttractionDetailsSubcategory> attractionDetailsSubcategoryListSanJuan = new ArrayList<>();
        attractionDetailsSubcategoryListSanJuan.add(attractionDetailsSubcategorySanJuan);

        AttractionDetailsOfferList attractionDetailsOfferListSanJuan = new AttractionDetailsOfferList("€70", "One-day trip");

        List<AttractionDetailsOfferList> attractionDetailsOfferListListSanJuan = new ArrayList<>();
        attractionDetailsOfferListListSanJuan.add(attractionDetailsOfferListSanJuan);

        AttractionDetailsOfferGroup attractionDetailsOfferGroupSanJuan = new AttractionDetailsOfferGroup(attractionDetailsOfferListListSanJuan);

        AttractionDetailsData attractionDetailsDataSanJuan = new AttractionDetailsData("111", "San Juan",
                "Nice place", "Alicante", attractionDetailsSubcategoryListSanJuan, attractionDetailsOfferGroupSanJuan);

        List<AttractionDetailsData> attractionDetailsDataList = new ArrayList<>();
        attractionDetailsDataList.add(attractionDetailsDataSanJuan);

        AttractionDetailsResults attractionDetailsResults = new AttractionDetailsResults(attractionDetailsDataList);

        return new AttractionDetailsResponse(attractionDetailsResults);
    }
}