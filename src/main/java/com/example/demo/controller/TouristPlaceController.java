package com.example.demo.controller;

import com.example.demo.dto.TouristPlaceDTO;
import com.example.demo.service.TouristPlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tourist-places")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Tourist Places", description = "Provides methods for managing tourist places")
public class TouristPlaceController {

    @Autowired
    private TouristPlaceService service;

    @Operation(summary = "Get all tourist places")
    @ApiResponse(responseCode = "200", description = "Found places",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = TouristPlaceDTO.class))))
    @GetMapping
    public List<TouristPlaceDTO> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get tourist place by ID")
    @ApiResponse(responseCode = "200", description = "Place found",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TouristPlaceDTO.class)))
    @GetMapping("{idPlace}")
    public ResponseEntity<TouristPlaceDTO> getById(@PathVariable Integer idPlace) {
        return new ResponseEntity<>(service.getById(idPlace), HttpStatus.OK);
    }

    @Operation(summary = "Get tourist places by municipality id")
    @ApiResponse(responseCode = "200", description = "Found places by municipality",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = TouristPlaceDTO.class))))
    @GetMapping("byMunicipality/{municipalityId}")
    public List<TouristPlaceDTO> getByMunicipality(@PathVariable Integer municipalityId) {
        return service.getByMunicipality(municipalityId);
    }

    @Operation(summary = "Create a tourist place")
    @ApiResponse(responseCode = "201", description = "Place created",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TouristPlaceDTO.class)))
    @PostMapping
    public ResponseEntity<TouristPlaceDTO> add(@Valid @RequestBody TouristPlaceDTO dto) {
        TouristPlaceDTO saved = service.save(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a tourist place")
    @ApiResponse(responseCode = "200", description = "Place updated",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TouristPlaceDTO.class)))
    @PutMapping("{idPlace}")
    public ResponseEntity<TouristPlaceDTO> update(@Valid @RequestBody TouristPlaceDTO dto, @PathVariable Integer idPlace) {
        TouristPlaceDTO updated = service.update(dto, idPlace);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(summary = "Delete a tourist place")
    @ApiResponse(responseCode = "200", description = "Place deleted")
    @DeleteMapping("{idPlace}")
    public ResponseEntity<String> delete(@PathVariable Integer idPlace) {
        service.delete(idPlace);
        return new ResponseEntity<>("Deleted record", HttpStatus.OK);
    }
}