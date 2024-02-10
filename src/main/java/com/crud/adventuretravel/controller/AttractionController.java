package com.crud.adventuretravel.controller;

import com.crud.adventuretravel.domain.AttractionDto;
import com.crud.adventuretravel.exception.AttractionAlreadyExistsException;
import com.crud.adventuretravel.exception.AttractionNotFoundException;
import com.crud.adventuretravel.facade.AttractionFacade;
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

    private final AttractionFacade attractionFacade;

    @GetMapping
    public ResponseEntity<List<AttractionDto>> getAttractions() {

        return ResponseEntity.ok(attractionFacade.getAttractions());
    }

    @GetMapping(value = "{attractionId}")
    public ResponseEntity<AttractionDto> getAttractionById(@PathVariable Long attractionId) throws AttractionNotFoundException {

        return ResponseEntity.ok(attractionFacade.getAttractionById(attractionId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createAttraction(@RequestBody AttractionDto attractionDto) throws AttractionAlreadyExistsException {

        attractionFacade.createAttraction(attractionDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateAttraction(@RequestBody AttractionDto attractionDto) throws AttractionNotFoundException {

        attractionFacade.updateAttraction(attractionDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{attractionId}")
    public ResponseEntity<Void> deleteAttraction(@PathVariable Long attractionId) {

        attractionFacade.deleteAttraction(attractionId);
        return ResponseEntity.ok().build();
    }
}
