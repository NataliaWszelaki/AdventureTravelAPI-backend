package com.crud.adventuretravel.touristAttractionApi.controller;

import com.crud.adventuretravel.touristAttractionApi.domain.AttractionDetailsDto;
import com.crud.adventuretravel.touristAttractionApi.mapper.TouristAttractionApiMapper;
import com.crud.adventuretravel.touristAttractionApi.client.TouristAttractionAPIClient;
import com.crud.adventuretravel.touristAttractionApi.domain.LocationSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/tourist-attractions")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TouristAttractionApiController {

    private final TouristAttractionAPIClient touristAttractionAPIClient;
    private final TouristAttractionApiMapper touristAttractionApiMapper;

    @GetMapping(value = "location/{text}")
    public List<LocationSearchDto> getListOfLocationSearchDto(@PathVariable String text) throws IOException {

        String jsonString = touristAttractionAPIClient.fetchLocationId(text);
        return touristAttractionApiMapper.mapToLocationSearchDtoList(jsonString);
    }

    @GetMapping(value = "details/{locationId}")
    public List<AttractionDetailsDto> getListOfAttractionDetailsDto(@PathVariable int locationId) throws IOException, InterruptedException {
        return touristAttractionApiMapper.mapToAttractionsDetailsDtoList(touristAttractionAPIClient.fetchAttractionDetails(locationId));
    }
}
