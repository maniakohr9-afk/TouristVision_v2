package com.example.demo.controller;

import com.example.demo.dto.StateDTO;
import com.example.demo.service.StateService;
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
@RequestMapping("/api/states")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "States", description = "Provides methods for managing states")
public class StateController {

    @Autowired
    private StateService service;

    @Operation(summary = "Get all states")
    @ApiResponse(responseCode = "200", description = "Found states",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = StateDTO.class))))
    @GetMapping
    public List<StateDTO> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get state by ID")
    @ApiResponse(responseCode = "200", description = "State found",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = StateDTO.class)))
    @GetMapping("{idState}")
    public ResponseEntity<StateDTO> getById(@PathVariable Integer idState) {
        return new ResponseEntity<>(service.getById(idState), HttpStatus.OK);
    }

    @Operation(summary = "Search states by name")
    @ApiResponse(responseCode = "200", description = "States found",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = StateDTO.class))))
    @GetMapping("search/{name}")
    public List<StateDTO> searchByName(@PathVariable String name) {
        return service.searchByName(name);
    }

    @Operation(summary = "Create a state")
    @ApiResponse(responseCode = "201", description = "State created",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = StateDTO.class)))
    @PostMapping
    public ResponseEntity<StateDTO> add(@Valid @RequestBody StateDTO dto) {
        StateDTO saved = service.save(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a state")
    @ApiResponse(responseCode = "200", description = "State updated",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = StateDTO.class)))
    @PutMapping("{idState}")
    public ResponseEntity<StateDTO> update(@Valid @RequestBody StateDTO dto, @PathVariable Integer idState) {
        StateDTO updated = service.update(dto, idState);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(summary = "Delete a state")
    @ApiResponse(responseCode = "200", description = "State deleted")
    @DeleteMapping("{idState}")
    public ResponseEntity<String> delete(@PathVariable Integer idState) {
        service.delete(idState);
        return new ResponseEntity<>("Deleted record", HttpStatus.OK);
    }
}
