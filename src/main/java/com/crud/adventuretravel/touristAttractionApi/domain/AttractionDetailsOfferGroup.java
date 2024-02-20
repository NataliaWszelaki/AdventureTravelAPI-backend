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
    private String lowestPrice;

    @JsonProperty("offer_list")
    private List<AttractionDetailsOfferList> attractionDetailsOfferListList;

    @JsonProperty("has_see_all_url")
    private boolean hasSeeAllUrl;

    @JsonProperty("is_eligible_for_ap_list")
    private boolean isEligibleForApList;

    public AttractionDetailsOfferGroup(List<AttractionDetailsOfferList> attractionDetailsOfferListList) {
        this.attractionDetailsOfferListList = attractionDetailsOfferListList;
    }
}
