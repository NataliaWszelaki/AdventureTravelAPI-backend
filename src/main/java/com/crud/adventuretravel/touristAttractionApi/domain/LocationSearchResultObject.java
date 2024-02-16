package com.crud.adventuretravel.touristAttractionApi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationSearchResultObject {

    @JsonProperty("location_id")
    public String locationId;
    @JsonProperty("name")
    public String name;
//    @JsonProperty("latitude")
//    public String latitude;
//    @JsonProperty("longitude")
//    public String longitude;
//    @JsonProperty("timezone")
//    public String timezone;
//    @JsonProperty("location_string")
//    public String locationString;
//    @JsonProperty("photo")
//    public Photo photo;
//    @JsonProperty("default_options")
//    public List<DefaultOption> defaultOptions;
//    @JsonProperty("geo_type")
//    public String geoType;
//    @JsonProperty("location_subtype")
//    public String locationSubtype;
//    @JsonProperty("has_restaurant_coverpage")
//    public Boolean hasRestaurantCoverpage;
//    @JsonProperty("has_attraction_coverpage")
//    public Boolean hasAttractionCoverpage;
//    @JsonProperty("has_curated_shopping_list")
//    public Boolean hasCuratedShoppingList;
//    @JsonProperty("show_address")
//    public Boolean showAddress;
//    @JsonProperty("preferred_map_engine")
//    public String preferredMapEngine;
//    @JsonProperty("description")
//    public String description;
//    @JsonProperty("is_localized_description")
//    public Boolean isLocalizedDescription;
    @JsonProperty("ancestors")
    public List<LocationSearchAncestor> locationSearchAncestors;
//    @JsonProperty("parent_display_name")
//    public String parentDisplayName;
//    @JsonProperty("guide_count")
//    public String guideCount;
}
