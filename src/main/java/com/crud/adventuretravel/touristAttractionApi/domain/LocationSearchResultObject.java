package com.crud.adventuretravel.touristAttractionApi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationSearchResultObject {

    @JsonProperty("location_id")
    private String locationId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("ancestors")
    private List<LocationSearchAncestor> locationSearchAncestors;
}
