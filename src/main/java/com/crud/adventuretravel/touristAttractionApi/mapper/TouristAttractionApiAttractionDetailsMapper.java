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
public class TouristAttractionApiAttractionDetailsMapper {

    private final ObjectMapper objectMapper;

    public AttractionDetailsResponse mapToAttractiondetailsResponse(String jsonString) {

        try {
            return objectMapper.readValue(jsonString, AttractionDetailsResponse.class);
        } catch (IOException e) {
            log.error("An error occurred while mapping to AttractionDetailsResponse {}", e.getMessage(), e);
            return new AttractionDetailsResponse();
        }
    }

    public List<AttractionDetailsDto> mapToAttractionsDetailsDtoList(AttractionDetailsResponse attractionDetailsResponse) {

        List<AttractionDetailsDto> attractionDetailsDtoList = new ArrayList<>();
        for (AttractionDetailsData attractionDetailsData : attractionDetailsResponse.getAttractionDetailsResults().getAttractionDetailsDataList()) {
            try {
                if (isValidData(attractionDetailsData) ) {
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
                                            Integer.parseInt(offerList.getRoundedUpPrice().replaceAll("\\D", ""))
                                    ));
                        }
                    }
                } catch (Exception e) {
                log.error("An error occurred: {}", e.getMessage(), e);
            }
        }
        return attractionDetailsDtoList;
    }

    private boolean isValidData(AttractionDetailsData attractionDetailsData) {

        AttractionDetailsOfferGroup attractionDetailsOfferGroup = attractionDetailsData.getAttractionDetailsOfferGroup();
        String description = attractionDetailsData.getDescription();
        return description != null && description.length() < 255 && attractionDetailsOfferGroup != null;
    }
}