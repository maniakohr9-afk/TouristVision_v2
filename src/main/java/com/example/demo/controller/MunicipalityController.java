package com.example.demo.controller;

import com.example.demo.dto.MunicipalityDTO;
import com.example.demo.service.MunicipalityService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/municipalities")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Municipalities", description = "Provides methods for managing municipalities")
public class MunicipalityController {

    @Autowired
    private MunicipalityService service;

    @Operation(summary = "Get all municipalities")
    @ApiResponse(responseCode = "200", description = "Found municipalities",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = MunicipalityDTO.class))))
    @GetMapping
    public List<MunicipalityDTO> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get municipality by ID")
    @ApiResponse(responseCode = "200", description = "Found municipality",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = MunicipalityDTO.class)))
    @GetMapping("{idMunicipality}")
    public ResponseEntity<MunicipalityDTO> getById(@PathVariable Integer idMunicipality) {
        return new ResponseEntity<>(service.getById(idMunicipality), HttpStatus.OK);
    }

    @Operation(summary = "Get municipalities by state id")
    @ApiResponse(responseCode = "200", description = "Found municipalities by state",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = MunicipalityDTO.class))))
    @GetMapping("byState/{stateId}")
    public List<MunicipalityDTO> getByState(@PathVariable Integer stateId) {
        return service.getByState(stateId);
    }

    @Operation(summary = "Create a municipality")
    @ApiResponse(responseCode = "201", description = "Municipality created",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = MunicipalityDTO.class)))
    @PostMapping
    public ResponseEntity<MunicipalityDTO> add(@Valid @RequestBody MunicipalityDTO dto) {
        MunicipalityDTO saved = service.save(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a municipality")
    @ApiResponse(responseCode = "200", description = "Municipality updated",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = MunicipalityDTO.class)))
    @PutMapping("{idMunicipality}")
    public ResponseEntity<MunicipalityDTO> update(@Valid @RequestBody MunicipalityDTO dto, @PathVariable Integer idMunicipality) {
        MunicipalityDTO updated = service.update(dto, idMunicipality);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(summary = "Delete a municipality")
    @ApiResponse(responseCode = "200", description = "Municipality deleted")
    @DeleteMapping("{idMunicipality}")
    public ResponseEntity<String> delete(@PathVariable Integer idMunicipality) {
        service.delete(idMunicipality);
        return new ResponseEntity<>("Deleted record", HttpStatus.OK);
    }
}
