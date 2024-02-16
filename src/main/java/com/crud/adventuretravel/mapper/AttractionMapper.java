package com.crud.adventuretravel.mapper;

import com.crud.adventuretravel.domain.Attraction;
import com.crud.adventuretravel.domain.AttractionDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttractionMapper {

    public AttractionDto mapToAttractionDto(Attraction attraction) {

        return new AttractionDto(
                attraction.getId(),
                attraction.getLocation_id(),
                attraction.getCity(),
                attraction.getName(),
                attraction.getDescription(),
                attraction.getCategory(),
                attraction.getTitle(),
                attraction.getPriceEuro(),
                attraction.getPricePln()
        );
    }

    public Attraction mapToAttraction(AttractionDto attractionDto) {

        return new Attraction(
                attractionDto.getId(),
                attractionDto.getLocation_id(),
                attractionDto.getCity(),
                attractionDto.getName(),
                attractionDto.getDescription(),
                attractionDto.getCategory(),
                attractionDto.getTitle(),
                attractionDto.getPriceEuro(),
                attractionDto.getPricePln()
        );
    }

    public List<AttractionDto> mapToAttractionDtoList(List<Attraction> attractionList) {

        return attractionList.stream()
                .map(this::mapToAttractionDto)
                .collect(Collectors.toList());
    }
}
