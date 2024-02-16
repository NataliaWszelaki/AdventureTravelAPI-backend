package com.crud.adventuretravel.touristAttractionApi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationSearchResponse {

    @JsonProperty("status")
    public Integer status;
    @JsonProperty("msg")
    public Object msg;
    @JsonProperty("results")
    public LocationSearchResults locationSearchResults;

    public LocationSearchResponse(LocationSearchResults locationSearchResults) {
        this.locationSearchResults = locationSearchResults;
    }
}
