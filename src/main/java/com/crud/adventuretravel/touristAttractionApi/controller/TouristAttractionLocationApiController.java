package com.crud.adventuretravel.touristAttractionApi.controller;

import com.crud.adventuretravel.touristAttractionApi.client.TouristAttractionAPIClient;
import com.crud.adventuretravel.touristAttractionApi.domain.LocationSearchDto;
import com.crud.adventuretravel.touristAttractionApi.domain.LocationSearchResponse;
import com.crud.adventuretravel.touristAttractionApi.mapper.TouristAttractionApiLocationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tourist-attractions")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TouristAttractionLocationApiController {

    private final TouristAttractionAPIClient touristAttractionAPIClient;
    private final TouristAttractionApiLocationMapper touristAttractionApiLocationMapper;

    @GetMapping(value = "location/{text}")
    public List<LocationSearchDto> getListOfLocationSearchDto(@PathVariable String text) {

        String jsonString = touristAttractionAPIClient.fetchLocationId(text);
        LocationSearchResponse locationSearchResponse = touristAttractionApiLocationMapper.mapToLocationSearchResponse(jsonString);
        return touristAttractionApiLocationMapper.mapToLocationSearchDtoList(locationSearchResponse);
    }
}
