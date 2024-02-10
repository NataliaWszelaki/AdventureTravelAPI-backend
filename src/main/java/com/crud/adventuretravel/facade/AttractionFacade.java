package com.crud.adventuretravel.facade;

import com.crud.adventuretravel.domain.Attraction;
import com.crud.adventuretravel.domain.AttractionDto;
import com.crud.adventuretravel.exception.AttractionAlreadyExistsException;
import com.crud.adventuretravel.exception.AttractionNotFoundException;
import com.crud.adventuretravel.mapper.AttractionMapper;
import com.crud.adventuretravel.service.AttractionDBService;
import com.crud.adventuretravel.subscriber.NewAttractionNotifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AttractionFacade {

    private final AttractionDBService attractionDBService;
    private final AttractionMapper attractionMapper;
    private final NewAttractionNotifier newAttractionNotifier;

    public List<AttractionDto> getAttractions() {

        List<Attraction> attractionList = attractionDBService.getAllAttractions();
        return attractionMapper.mapToAttractionDtoList(attractionList);
    }

    public AttractionDto getAttractionById(Long attractionId) throws AttractionNotFoundException {
        
        Attraction attraction = attractionDBService.getAttractionById(attractionId);
        return attractionMapper.mapToAttractionDto(attraction);
    }
    
    public void createAttraction(AttractionDto attractionDto) throws AttractionAlreadyExistsException {

        Attraction attraction = attractionMapper.mapToAttraction(attractionDto);
        newAttractionNotifier.notifyObservers();
        attractionDBService.createAttraction(attraction);
    }
    
    public void updateAttraction(AttractionDto attractionDto) throws AttractionNotFoundException {

        Attraction attraction = attractionMapper.mapToAttraction(attractionDto);
        attractionDBService.updateAttraction(attraction);
    }
    
    public void deleteAttraction(long attractionId) {

        attractionDBService.deleteAttraction(attractionId);
    }
}
