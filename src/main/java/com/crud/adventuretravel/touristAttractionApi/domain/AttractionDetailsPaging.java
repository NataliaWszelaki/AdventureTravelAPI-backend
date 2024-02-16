package com.crud.adventuretravel.touristAttractionApi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttractionDetailsPaging {

    @JsonProperty("previous")
    private String previous;
    
    @JsonProperty("next")
    private String next;
    
    @JsonProperty("skipped")
    private String skipped;
    
    @JsonProperty("results")
    private String results;
    
    @JsonProperty("total_results")
    private String totalResults;
}
