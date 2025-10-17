package com.example.demo.controller;

import com.example.demo.dto.GastronomyDTO;
import com.example.demo.service.GastronomyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gastronomies")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Gastronomies", description = "Provides methods for managing gastronomy dishes")
public class GastronomyController {

    @Autowired
    private GastronomyService service;

    @Operation(summary = "Get all dishes")
    @ApiResponse(responseCode = "200", description = "Found dishes",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = GastronomyDTO.class))))
    @GetMapping
    public List<GastronomyDTO> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get dish by ID")
    @ApiResponse(responseCode = "200", description = "Found dish",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = GastronomyDTO.class)))
    @GetMapping("{idDish}")
    public ResponseEntity<GastronomyDTO> getById(@PathVariable Integer idDish) {
        return new ResponseEntity<>(service.getById(idDish), HttpStatus.OK);
    }

    @Operation(summary = "Get dishes by place id")
    @ApiResponse(responseCode = "200", description = "Found dishes by place",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = GastronomyDTO.class))))
    @GetMapping("byPlace/{placeId}")
    public List<GastronomyDTO> getByPlace(@PathVariable Integer placeId) {
        return service.getByPlace(placeId);
    }

    @Operation(summary = "Create a dish")
    @ApiResponse(responseCode = "201", description = "Dish created",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = GastronomyDTO.class)))
    @PostMapping
    public ResponseEntity<GastronomyDTO> add(@RequestBody GastronomyDTO dto) {
        GastronomyDTO saved = service.save(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a dish")
    @ApiResponse(responseCode = "200", description = "Dish updated",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = GastronomyDTO.class)))
    @PutMapping("{idDish}")
    public ResponseEntity<GastronomyDTO> update(@RequestBody GastronomyDTO dto, @PathVariable Integer idDish) {
        GastronomyDTO updated = service.update(dto, idDish);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(summary = "Delete a dish")
    @ApiResponse(responseCode = "200", description = "Dish deleted")
    @DeleteMapping("{idDish}")
    public ResponseEntity<String> delete(@PathVariable Integer idDish) {
        service.delete(idDish);
        return new ResponseEntity<>("Deleted record", HttpStatus.OK);
    }
}
