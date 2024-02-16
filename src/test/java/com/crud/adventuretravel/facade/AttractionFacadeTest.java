package com.crud.adventuretravel.facade;

import com.crud.adventuretravel.domain.Attraction;
import com.crud.adventuretravel.domain.AttractionDto;
import com.crud.adventuretravel.exception.AttractionAlreadyExistsException;
import com.crud.adventuretravel.exception.AttractionNotFoundException;
import com.crud.adventuretravel.mapper.AttractionMapper;
import com.crud.adventuretravel.service.AttractionDBService;
import com.crud.adventuretravel.subscriber.NewAttractionNotifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AttractionFacadeTest {

    @MockBean
    private AttractionDBService attractionDBService;

    @MockBean
    private AttractionMapper attractionMapper;

    @MockBean
    private NewAttractionNotifier newAttractionNotifier;

    @Autowired
    private AttractionFacade attractionFacade;

    private Attraction attraction;
    private AttractionDto attractionDto;

    @BeforeEach
    void setUp() {

        attraction = new Attraction(5L, 123, "Isola del Giglio", "Isola del Giglio", "Making pasta",
                "Cooking", "How to make pasta", 30, 150);

        attractionDto = new AttractionDto(5L, 123, "Isola del Giglio", "Isola del Giglio", "Making pasta",
                "Cooking", "How to make pasta", 30, 150);
    }

    @Test
    void shouldFetchAttractionList() {

        //Given
        List<Attraction> attractionList = List.of(
                attraction,
                new Attraction(8L, 234, "Sienna", "Wine tasting", "Vineyard",
                        "Tasting Wine in a beautiful restaurant", "Private tour", 30, 150));
        List<AttractionDto> attractionDtoList = List.of(
                attractionDto,
                new AttractionDto(8L, 234, "Sienna", "Wine tasting", "Vineyard",
                        "Tasting Wine in a beautiful restaurant", "Private tour", 30, 150));
        when(attractionDBService.getAllAttractions()).thenReturn(attractionList);
        when(attractionMapper.mapToAttractionDtoList(attractionList)).thenReturn(attractionDtoList);

        //When
        List<AttractionDto> returnedAttractionDtoList = attractionFacade.getAttractions();

        //Then
        assertEquals(2, returnedAttractionDtoList.size());
    }

    @Test
    void shouldFetchAttractionById() throws AttractionNotFoundException {

        //Given
        long attractionId = 5L;
        when(attractionDBService.getAttractionById(attractionId)).thenReturn(attraction);
        when(attractionMapper.mapToAttractionDto(attraction)).thenReturn(attractionDto);

        //When
        AttractionDto returnedAttractionDto = attractionFacade.getAttractionById(attractionId);

        //Then
        assertEquals("Isola del Giglio", returnedAttractionDto.getName());
    }

    @Test
    void shouldNotFetchAttractionById() throws AttractionNotFoundException {

        //Given
        Long attractionId = 223L;
        when(attractionDBService.getAttractionById(attractionId)).thenThrow(AttractionNotFoundException.class);

        //When&Then
        assertThrows(AttractionNotFoundException.class, () -> attractionFacade.getAttractionById(attractionId));
    }

    @Test
    void shouldCreateAttraction() throws AttractionAlreadyExistsException {

        //Given
        when(attractionMapper.mapToAttraction(attractionDto)).thenReturn(attraction);

        //When
        attractionFacade.createAttraction(attractionDto);

        //Then
        verify(newAttractionNotifier, times(1)).notifyObservers();
        verify(attractionDBService, times(1)).createAttraction(attraction);
    }

    @Test
    void shouldUpdateAttraction() throws Exception {

        //Given
        when(attractionMapper.mapToAttraction(attractionDto)).thenReturn(attraction);

        //When
        attractionFacade.updateAttraction(attractionDto);

        //Then
        verify(attractionDBService, times(1)).updateAttraction(attraction);
    }

    @Test
    void shouldDeleteAttraction() {

        //Given
        long attractionId = 5L;

        //When
        attractionFacade.deleteAttraction(attractionId);

        //Then
        verify(attractionDBService, times(1)).deleteAttraction(attractionId);
    }
}