package com.crud.adventuretravel.touristAttractionApi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttractionDetailsResults {

    @JsonProperty("data")
    private List<AttractionDetailsData> attractionDetailsDataList;

    @JsonProperty("paging")
    private AttractionDetailsPaging attractionDetailsPaging;

    public AttractionDetailsResults(List<AttractionDetailsData> attractionDetailsDataList) {
        this.attractionDetailsDataList = attractionDetailsDataList;
    }
}
