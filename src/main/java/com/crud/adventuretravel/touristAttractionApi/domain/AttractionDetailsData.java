package com.crud.adventuretravel.touristAttractionApi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttractionDetailsData {

    @JsonProperty("location_id")
    private String location_id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("parent_display_name")
    private String parentDisplayName;

    @JsonProperty("subcategory")
    private List<AttractionDetailsSubcategory> attractionDetailsSubcategoryList;

    @JsonProperty("offer_group")
    private AttractionDetailsOfferGroup attractionDetailsOfferGroup;
}
