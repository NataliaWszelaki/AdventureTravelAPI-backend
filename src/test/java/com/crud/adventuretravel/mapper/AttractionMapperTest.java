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
        Attraction attraction = new Attraction(5L, "Making pasta", "Isola del Giglio",
                "How to make pasta", 30, 150);

        //When
        AttractionDto mappedAttractionDto = attractionMapper.mapToAttractionDto(attraction);

        //Then
        assertEquals(5, mappedAttractionDto.getId());
        assertEquals("Making pasta", mappedAttractionDto.getName());
        assertEquals("Isola del Giglio", mappedAttractionDto.getCity());
        assertEquals("How to make pasta", mappedAttractionDto.getDescription());
        assertEquals(30, mappedAttractionDto.getPriceEuro());
        assertEquals(150, mappedAttractionDto.getPricePln());
    }

    @Test
    void shouldMapToAttraction() {

        //Given
        AttractionDto attractionDto = new AttractionDto(5L, "Making pasta", "Isola del Giglio",
                "How to make pasta", 30, 150);

        //When
        Attraction mappedAttraction = attractionMapper.mapToAttraction(attractionDto);

        //Then
        assertEquals(5, mappedAttraction.getId());
        assertEquals("Making pasta", mappedAttraction.getName());
        assertEquals("Isola del Giglio", mappedAttraction.getCity());
        assertEquals("How to make pasta", mappedAttraction.getDescription());
        assertEquals(30, mappedAttraction.getPriceEuro());
        assertEquals(150, mappedAttraction.getPricePln());
    }

    @Test
    void shouldMapToAttractionDtoList() {

        //Given
        List<Attraction> attractionList = List.of(
                new Attraction(5L, "Making pasta", "Isola del Giglio",
                        "How to make pasta", 30, 150),
                new Attraction(8L, "Wine tasting", "Sienna",
                        "tasting Wine in a beautiful restaurant", 31, 151));

        //When
        List<AttractionDto> attractionDtoList = attractionMapper.mapToAttractionDtoList(attractionList);

        //Then
        assertEquals(2, attractionDtoList.size());
        assertEquals(5, attractionDtoList.get(0).getId());
        assertEquals(8, attractionDtoList.get(1).getId());
        assertEquals("Making pasta", attractionDtoList.get(0).getName());
        assertEquals("Wine tasting", attractionDtoList.get(1).getName());
        assertEquals("Isola del Giglio", attractionDtoList.get(0).getCity());
        assertEquals("tasting Wine in a beautiful restaurant", attractionDtoList.get(1).getDescription());
        assertEquals(31, attractionDtoList.get(1).getPriceEuro());
        assertEquals(151, attractionDtoList.get(1).getPricePln());
    }
}