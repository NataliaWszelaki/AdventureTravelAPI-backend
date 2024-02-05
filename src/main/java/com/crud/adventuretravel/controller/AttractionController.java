package com.crud.adventuretravel.controller;

import com.crud.adventuretravel.domain.Attraction;
import com.crud.adventuretravel.domain.AttractionDto;
import com.crud.adventuretravel.exception.AttractionAlreadyExistsException;
import com.crud.adventuretravel.exception.AttractionNotFoundException;
import com.crud.adventuretravel.mapper.AttractionMapper;
import com.crud.adventuretravel.service.AttractionDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/attractions")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AttractionController {

    private final AttractionDBService attractionDBService;
    private final AttractionMapper attractionMapper;

    @GetMapping
    public List<AttractionDto> getAttractions() {

        List<Attraction> attractionList = attractionDBService.getAllAttractions();
        return attractionMapper.mapToAttractionDtoList(attractionList);
    }

    @GetMapping(value = "{attractionId}")
    public ResponseEntity<AttractionDto> getAttractionById(@PathVariable Long attractionId) throws AttractionNotFoundException {

        Attraction attraction = attractionDBService.getAttractionById(attractionId);
        return ResponseEntity.ok(attractionMapper.mapToAttractionDto(attraction));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createAttraction(@RequestBody AttractionDto attractionDto) throws AttractionAlreadyExistsException {

        Attraction attraction = attractionMapper.mapToAttraction(attractionDto);
        attractionDBService.createAttraction(attraction);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateAttraction(@RequestBody AttractionDto attractionDto) throws AttractionNotFoundException {

        Attraction attraction = attractionMapper.mapToAttraction(attractionDto);
        attractionDBService.updateAttraction(attraction);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{attractionId}")
    public ResponseEntity<Void> deleteAttraction(@PathVariable Long attractionId) {

        attractionDBService.deleteAttraction(attractionId);
        return ResponseEntity.ok().build();
    }
}
