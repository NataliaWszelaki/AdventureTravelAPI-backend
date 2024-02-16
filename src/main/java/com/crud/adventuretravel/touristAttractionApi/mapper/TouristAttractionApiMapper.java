package com.crud.adventuretravel.touristAttractionApi.mapper;

import com.crud.adventuretravel.touristAttractionApi.domain.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TouristAttractionApiMapper {

    private final ObjectMapper objectMapper;

    public List<LocationSearchDto> mapToLocationSearchDtoList(String jsonString) throws IOException {

        LocationSearchResponse locationSearchResponse = objectMapper.readValue(jsonString, LocationSearchResponse.class);

        List<LocationSearchDto> locationSearchDtoList = new ArrayList<>();
        String country = "";
        for (LocationSearchData locationSearchData : locationSearchResponse.getLocationSearchResults().getLocationSearchDataList()) {
            try {
                for (LocationSearchAncestor locationSearchAncestor : locationSearchData.getLocationSearchResultObject().getLocationSearchAncestors()) {
                    if (locationSearchAncestor.getLocationSearchSubcategoryList().get(0).getKey().equals("country")) {
                        country = locationSearchAncestor.getName();
                    }
                }
                locationSearchDtoList.add(new LocationSearchDto(
                        Integer.parseInt(locationSearchData.getLocationSearchResultObject().getLocationId()),
                        locationSearchData.getLocationSearchResultObject().getName(),
                        locationSearchData.getLocationSearchResultObject().getLocationSearchAncestors().get(0).getName(),
                        country
                ));
            } catch (Exception e) {
                log.error("An error occurred: {}", e.getMessage(), e);
            }
        }
        return locationSearchDtoList;
    }

    public List<AttractionDetailsDto> mapToAttractionsDetailsDtoList(String jsonString) throws IOException {

        AttractionDetailsResponse attractionDetailsResponse = objectMapper.readValue(jsonString, AttractionDetailsResponse.class);
        List<AttractionDetailsDto> attractionDetailsDtoList = new ArrayList<>();
        for (AttractionDetailsData attractionDetailsData : attractionDetailsResponse.getAttractionDetailsResults().getAttractionDetailsDataList()) {
            try {
                List<AttractionDetailsOfferList> offerLists = attractionDetailsData.getAttractionDetailsOfferGroup().getAttractionDetailsOfferListList();
                for (AttractionDetailsOfferList offerList : offerLists) {
                    attractionDetailsDtoList.add(
                            new AttractionDetailsDto(
                                    Integer.parseInt(attractionDetailsData.getLocation_id()),
                                    attractionDetailsData.getParentDisplayName(),
                                    attractionDetailsData.getName(),
                                    attractionDetailsData.getDescription(),
                                    attractionDetailsData.getName(),
                                    offerList.getTitle(),
                                    Integer.parseInt((offerList.getRoundedUpPrice()).substring(1))
                            ));
                }
            } catch (Exception e) {
                log.error("An error occurred: {}", e.getMessage(), e);
            }
        }
        return attractionDetailsDtoList;
    }
}
