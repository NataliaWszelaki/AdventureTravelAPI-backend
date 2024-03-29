package com.crud.adventuretravel.service;

import com.crud.adventuretravel.domain.Attraction;
import com.crud.adventuretravel.domain.AttractionDto;
import com.crud.adventuretravel.exception.AttractionAlreadyExistsException;
import com.crud.adventuretravel.exception.AttractionNotFoundException;
import com.crud.adventuretravel.repository.AttractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttractionDBService {

    private final AttractionRepository attractionRepository;

    public List<Attraction> getAllAttractions() {

        return attractionRepository.findAll();
    }

    public Attraction getAttractionById(Long attractionId) throws AttractionNotFoundException {

        return attractionRepository.findById(attractionId).orElseThrow(AttractionNotFoundException::new);
    }

    public void createAttraction(Attraction attraction) throws AttractionAlreadyExistsException {

        boolean isExisting = attractionRepository.existsByTitle(attraction.getTitle());
        if (!isExisting) {
            attraction.setActive(true);
            attractionRepository.save(attraction);
        } else {
            throw new AttractionAlreadyExistsException();
        }
    }

    public void updateAttraction(Attraction attraction) throws AttractionNotFoundException {

        Attraction searchedAttraction = attractionRepository.findById(attraction.getId()).orElse(null);
        if (searchedAttraction != null) {
            attraction.setActive(true);
            attractionRepository.save(attraction);
        } else {
            throw new AttractionNotFoundException();
        }
    }

    public void updateAttractionDeactivate(AttractionDto attractionDto) throws AttractionNotFoundException {

        Attraction attraction = attractionRepository.findById(attractionDto.getId()).orElse(null);
        if (attraction != null) {
            attraction.setActive(false);
            attractionRepository.save(attraction);
        } else {
            throw new AttractionNotFoundException();
        }
    }

    public void deleteAttraction(Long attractionId) {

        attractionRepository.deleteById(attractionId);
    }
}
