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
    public String location_id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("description")
    public String description;
    @JsonProperty("parent_display_name")
    public String parentDisplayName;
    @JsonProperty("subcategory")
    public List<AttractionDetailsSubcategory> attractionDetailsSubcategoryList;
    @JsonProperty("offer_group")
    public AttractionDetailsOfferGroup attractionDetailsOfferGroup;
}
