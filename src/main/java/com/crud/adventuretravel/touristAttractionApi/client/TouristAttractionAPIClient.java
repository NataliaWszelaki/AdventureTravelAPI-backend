package com.crud.adventuretravel.touristAttractionApi.client;

import com.crud.adventuretravel.touristAttractionApi.config.TouristAttractionApiConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class TouristAttractionAPIClient {

    private final TouristAttractionApiConfig touristAttractionApiConfig;

    public String fetchLocationId(String text) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(touristAttractionApiConfig.getTouristAttractionApiAPIEndpoint() + "typeahead"))
                .header("content-type", touristAttractionApiConfig.getTouristAttractionApiAppContentType())
                .header("X-RapidAPI-Key", touristAttractionApiConfig.getTouristAttractionApiAppKey())
                .header("X-RapidAPI-Host", touristAttractionApiConfig.getTouristAttractionApiAppHost())
                .method("POST", HttpRequest.BodyPublishers.ofString("q=" + text + "&language=en_GB"))
                .build();
        return executeHttpRequestAndGetStringResponse(request);
    }

    public String fetchAttractionDetails(int locationId) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(touristAttractionApiConfig.getTouristAttractionApiAPIEndpoint() + "search"))
                .header("content-type", touristAttractionApiConfig.getTouristAttractionApiAppContentType())
                .header("X-RapidAPI-Key", touristAttractionApiConfig.getTouristAttractionApiAppKey())
                .header("X-RapidAPI-Host", touristAttractionApiConfig.getTouristAttractionApiAppHost())
                .method("POST", HttpRequest.BodyPublishers.ofString("location_id=" + locationId + "&language=en_GB&currency=EUR&offset=0"))
                .build();
        return executeHttpRequestAndGetStringResponse(request);
    }

    protected String executeHttpRequestAndGetStringResponse(HttpRequest request) {

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            if (statusCode == 200) {
                log.info("Response status code: " + statusCode);
                return response.body();
            } else {
                log.error("Error response status code: {}", statusCode);
                return null;
            }
        } catch (Exception e) {
            log.error("An error occurred: {}", e.getMessage(), e);
            return null;
        }
    }
}
