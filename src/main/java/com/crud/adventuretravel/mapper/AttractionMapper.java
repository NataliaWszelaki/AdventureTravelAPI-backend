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
                attraction.getName(),
                attraction.getCity(),
                attraction.getDescription(),
                attraction.getPriceEuro(),
                attraction.getPricePln()
        );
    }

    public Attraction mapToAttraction(AttractionDto attractionDto) {

        return new Attraction(
                attractionDto.getId(),
                attractionDto.getName(),
                attractionDto.getCity(),
                attractionDto.getDescription(),
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
