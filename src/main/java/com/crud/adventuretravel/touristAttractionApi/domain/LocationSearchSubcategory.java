package com.crud.adventuretravel.touristAttractionApi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LocationSearchSubcategory {

    @JsonProperty("key")
    private String key;

    @JsonProperty("name")
    private String name;
}
