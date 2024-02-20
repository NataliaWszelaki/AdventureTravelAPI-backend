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
public class TouristAttractionApiLocationMapper {

    private final ObjectMapper objectMapper;

    public LocationSearchResponse mapToLocationSearchResponse(String jsonString) {

        try {
            return objectMapper.readValue(jsonString, LocationSearchResponse.class);
        } catch (IOException e) {
            log.error("An error occurred while mapping to LocationSearchResponse {}", e.getMessage(), e);
            return new LocationSearchResponse();
        }
    }

    public List<LocationSearchDto> mapToLocationSearchDtoList(LocationSearchResponse locationSearchResponse) {

        List<LocationSearchDto> locationSearchDtoList = new ArrayList<>();
        for (LocationSearchData locationSearchData : locationSearchResponse.getLocationSearchResults().getLocationSearchDataList()) {
            try {

                if (isNotNull(locationSearchData)) {
                    String country = extractCountry(locationSearchData.getLocationSearchResultObject().getLocationSearchAncestors());
                    locationSearchDtoList.add(
                            new LocationSearchDto(
                                    Integer.parseInt(locationSearchData.getLocationSearchResultObject().getLocationId()),
                                    locationSearchData.getLocationSearchResultObject().getName(),
                                    locationSearchData.getLocationSearchResultObject().getLocationSearchAncestors().get(0).getName(),
                                    country
                            ));
                }
            } catch (Exception e) {
                log.error("An error occurred: {}", e.getMessage(), e);
            }
        }
        return locationSearchDtoList;
    }

    private String extractCountry(List<LocationSearchAncestor> locationSearchAncestorList) {

        String country = "";
        for (LocationSearchAncestor ancestor : locationSearchAncestorList) {
            if (ancestor.getLocationSearchSubcategoryList().get(0).getKey().equals("country")) {
                country = ancestor.getName();
            }
        }
        return country;
    }

    private boolean isNotNull(LocationSearchData locationSearchData) {

        List<LocationSearchAncestor> locationSearchAncestorList = locationSearchData.getLocationSearchResultObject().getLocationSearchAncestors();
        String location_id = locationSearchData.getLocationSearchResultObject().getLocationId();

        return locationSearchAncestorList != null && location_id != null && !location_id.equals("");
    }
}