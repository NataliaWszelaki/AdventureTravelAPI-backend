package com.crud.adventuretravel.touristAttractionApi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationSearchResults {

    @JsonProperty("data")
    public List<LocationSearchData> locationSearchDataList;
    @JsonProperty("partial_content")
    public Boolean partialContent;

    public LocationSearchResults(List<LocationSearchData> locationSearchDataList) {
        this.locationSearchDataList = locationSearchDataList;
    }
}
