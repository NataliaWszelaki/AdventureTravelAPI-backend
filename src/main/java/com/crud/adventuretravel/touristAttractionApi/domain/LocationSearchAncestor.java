package com.crud.adventuretravel.touristAttractionApi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationSearchAncestor {

    @JsonProperty("subcategory")
    private List<LocationSearchSubcategory> locationSearchSubcategoryList;

    @JsonProperty("name")
    private String name;

//    @JsonProperty("abbrv")
//    private Object abbrv;

//    @JsonProperty("location_id")
//    private String locationId;
//
//    public LocationSearchAncestor(List<LocationSearchSubcategory> locationSearchSubcategoryList, String name) {
//        this.locationSearchSubcategoryList = locationSearchSubcategoryList;
//        this.name = name;
//    }
}
