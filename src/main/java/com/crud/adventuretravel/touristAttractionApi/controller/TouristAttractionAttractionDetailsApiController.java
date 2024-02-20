package com.crud.adventuretravel.touristAttractionApi.controller;

import com.crud.adventuretravel.touristAttractionApi.client.TouristAttractionAPIClient;
import com.crud.adventuretravel.touristAttractionApi.domain.AttractionDetailsDto;
import com.crud.adventuretravel.touristAttractionApi.domain.AttractionDetailsResponse;
import com.crud.adventuretravel.touristAttractionApi.mapper.TouristAttractionApiAttractionDetailsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tourist-attractions")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TouristAttractionAttractionDetailsApiController {

    private final TouristAttractionAPIClient touristAttractionAPIClient;
    private final TouristAttractionApiAttractionDetailsMapper attractionApiAttractionDetailsMapper;

    @GetMapping(value = "details/{locationId}")
    public List<AttractionDetailsDto> getListOfAttractionDetailsDto(@PathVariable int locationId) {

        String jsonString = touristAttractionAPIClient.fetchAttractionDetails(locationId);
        AttractionDetailsResponse attractionDetailsResponse = attractionApiAttractionDetailsMapper.mapToAttractiondetailsResponse(jsonString);
        return attractionApiAttractionDetailsMapper.mapToAttractionsDetailsDtoList(attractionDetailsResponse);
    }
}
