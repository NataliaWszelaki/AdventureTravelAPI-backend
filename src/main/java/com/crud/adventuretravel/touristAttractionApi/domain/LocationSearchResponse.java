package com.crud.adventuretravel.touristAttractionApi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationSearchResponse {

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("msg")
    private Object msg;

    @JsonProperty("results")
    private LocationSearchResults locationSearchResults;

    public LocationSearchResponse(LocationSearchResults locationSearchResults) {
        this.locationSearchResults = locationSearchResults;
    }
}
