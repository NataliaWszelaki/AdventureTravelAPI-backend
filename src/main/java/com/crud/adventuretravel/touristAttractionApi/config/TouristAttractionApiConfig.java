package com.crud.adventuretravel.touristAttractionApi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class TouristAttractionApiConfig {

    @Value("${touristAttractionApi.api.endpoint.prod}")
    private String touristAttractionApiAPIEndpoint;

    @Value("${touristAttractionApi.app.key}")
    private String touristAttractionApiAppKey;

    @Value("${touristAttractionApi.app.host}")
    private String touristAttractionApiAppHost;

    @Value("${touristAttractionApi.app.contenttype}")
    private String touristAttractionApiAppContentType;
}
