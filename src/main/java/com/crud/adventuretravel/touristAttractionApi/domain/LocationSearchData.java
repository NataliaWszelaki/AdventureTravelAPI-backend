package com.crud.adventuretravel.touristAttractionApi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationSearchData {

    @JsonProperty("result_type")
    private String resultType;

    @JsonProperty("result_object")
    private LocationSearchResultObject locationSearchResultObject;

    @JsonProperty("scope")
    private String scope;

    public LocationSearchData(LocationSearchResultObject locationSearchResultObject) {
        this.locationSearchResultObject = locationSearchResultObject;
    }
}
