package com.crud.adventuretravel.touristAttractionApi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttractionDetailsSubcategory {

    @JsonProperty("key")
    private String key;

    @JsonProperty("name")
    private String name;
}
