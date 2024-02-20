package com.crud.adventuretravel.touristAttractionApi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttractionDetailsOfferList {

    @JsonProperty("url")
    private String url;

    @JsonProperty("price")
    private String price;

    @JsonProperty("rounded_up_price")
    private String roundedUpPrice;

//    @JsonProperty("offer_type")
//    private String offerType;

    @JsonProperty("title")
    private String title;

//    @JsonProperty("product_code")
//    private String productCode;

//    @JsonProperty("partner")
//    private String partner;

//    @JsonProperty("image_url")
//    private String imageUrl;

//    @JsonProperty("description")
//    private String description;

//    @JsonProperty("primary_category")
//    private String primaryCategory;

    public AttractionDetailsOfferList(String roundedUpPrice, String title) {
        this.roundedUpPrice = roundedUpPrice;
        this.title = title;
    }
}
