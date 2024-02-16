package com.crud.adventuretravel.touristAttractionApi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationSearchDto {

    private int location_id;
    private String name;
    private String ancestor;
    private String country;
}
