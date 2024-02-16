package com.crud.adventuretravel.touristAttractionApi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttractionDetailsOfferList {

    @JsonProperty("url")
    public String url;

    @JsonProperty("price")
    public String price;

    @JsonProperty("rounded_up_price")
    public String roundedUpPrice;

    @JsonProperty("offer_type")
    public String offerType;

    @JsonProperty("title")
    public String title;

    @JsonProperty("product_code")
    public String productCode;

    @JsonProperty("partner")
    public String partner;

    @JsonProperty("image_url")
    public String imageUrl;

    @JsonProperty("description")
    public String description;

    @JsonProperty("primary_category")
    public String primaryCategory;

    public AttractionDetailsOfferList(String roundedUpPrice, String title) {
        this.roundedUpPrice = roundedUpPrice;
        this.title = title;
    }
}
