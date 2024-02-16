package com.crud.adventuretravel.touristAttractionApi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttractionDetailsOfferGroup {

    @JsonProperty("lowest_price")
    public String lowestPrice;
    @JsonProperty("offer_list")
    public List<AttractionDetailsOfferList> attractionDetailsOfferListList;
    @JsonProperty("has_see_all_url")
    public boolean hasSeeAllUrl;
    @JsonProperty("is_eligible_for_ap_list")
    public boolean isEligibleForApList;

    public AttractionDetailsOfferGroup(List<AttractionDetailsOfferList> attractionDetailsOfferListList) {
        this.attractionDetailsOfferListList = attractionDetailsOfferListList;
    }
}
