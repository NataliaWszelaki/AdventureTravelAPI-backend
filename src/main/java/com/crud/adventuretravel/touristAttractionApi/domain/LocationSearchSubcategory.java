package com.crud.adventuretravel.touristAttractionApi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LocationSearchSubcategory {

    @JsonProperty("key")
    public String key;
    @JsonProperty("name")
    public String name;
}
