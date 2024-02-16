package com.crud.adventuretravel.touristAttractionApi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationSearchAncestor {

    @JsonProperty("subcategory")
    public List<LocationSearchSubcategory> locationSearchSubcategoryList;
    @JsonProperty("name")
    public String name;
    @JsonProperty("abbrv")
    public Object abbrv;
    @JsonProperty("location_id")
    public String locationId;

    public LocationSearchAncestor(List<LocationSearchSubcategory> locationSearchSubcategoryList, String name) {
        this.locationSearchSubcategoryList = locationSearchSubcategoryList;
        this.name = name;
    }
}
