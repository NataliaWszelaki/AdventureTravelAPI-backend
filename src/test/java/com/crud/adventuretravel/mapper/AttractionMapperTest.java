package com.crud.adventuretravel.mapper;

import com.crud.adventuretravel.domain.Attraction;
import com.crud.adventuretravel.domain.AttractionDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
class AttractionMapperTest {

    @Autowired
    private AttractionMapper attractionMapper;

    @Test
    void shouldMapToAttractionDto() {

        //Given
        Attraction attraction = new Attraction(5L, 123, "Isola del Giglio", "Isola del Giglio", "Making pasta",
                "Cooking", "How to make pasta", 30);

        //When
        AttractionDto mappedAttractionDto = attractionMapper.mapToAttractionDto(attraction);

        //Then
        assertEquals(5, mappedAttractionDto.getId());
        assertEquals("Isola del Giglio", mappedAttractionDto.getName());
        assertEquals("Isola del Giglio", mappedAttractionDto.getCity());
        assertEquals("Making pasta", mappedAttractionDto.getDescription());
        assertEquals(30, mappedAttractionDto.getPriceEuro());
    }

    @Test
    void shouldMapToAttraction() {

        //Given
        AttractionDto attractionDto = new AttractionDto(5L, 123, "Isola del Giglio", "Isola del Giglio", "Making pasta",
                "Cooking", "How to make pasta", 30);

        //When
        Attraction mappedAttraction = attractionMapper.mapToAttraction(attractionDto);

        //Then
        assertEquals(5, mappedAttraction.getId());
        assertEquals("Isola del Giglio", mappedAttraction.getName());
        assertEquals("Isola del Giglio", mappedAttraction.getCity());
        assertEquals("Making pasta", mappedAttraction.getDescription());
        assertEquals(30, mappedAttraction.getPriceEuro());
    }

    @Test
    void shouldMapToAttractionDtoList() {

        //Given
        List<Attraction> attractionList = List.of(
                new Attraction(5L, 123, "Isola del Giglio", "Isola del Giglio", "Making pasta",
                        "Cooking", "How to make pasta", 30),
                new Attraction(8L, 234, "Sienna", "Wine tasting", "Vineyard",
                        "Tasting Wine in a beautiful restaurant", "Private tour", 30));

        //When
        List<AttractionDto> attractionDtoList = attractionMapper.mapToAttractionDtoList(attractionList);

        //Then
        assertEquals(2, attractionDtoList.size());
        assertEquals(5, attractionDtoList.get(0).getId());
        assertEquals(8, attractionDtoList.get(1).getId());
        assertEquals("Isola del Giglio", attractionDtoList.get(0).getName());
        assertEquals("Wine tasting", attractionDtoList.get(1).getName());
        assertEquals("Isola del Giglio", attractionDtoList.get(0).getCity());
        assertEquals("Vineyard", attractionDtoList.get(1).getDescription());
        assertEquals(30, attractionDtoList.get(1).getPriceEuro());
    }
}