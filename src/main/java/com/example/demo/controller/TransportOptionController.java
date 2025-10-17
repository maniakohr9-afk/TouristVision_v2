package com.example.demo.controller;

import com.example.demo.dto.TransportOptionDTO;
import com.example.demo.service.TransportOptionService;
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
@RequestMapping("/api/transport-options")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Transport Options", description = "Provides methods for managing transport options of a place")
public class TransportOptionController {

    @Autowired
    private TransportOptionService service;

    @Operation(summary = "Get all transport options")
    @ApiResponse(responseCode = "200", description = "Found transport options",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = TransportOptionDTO.class))))
    @GetMapping
    public List<TransportOptionDTO> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get transport option by ID")
    @ApiResponse(responseCode = "200", description = "Transport option found",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TransportOptionDTO.class)))
    @GetMapping("{idTransport}")
    public ResponseEntity<TransportOptionDTO> getById(@PathVariable Integer idTransport) {
        return new ResponseEntity<>(service.getById(idTransport), HttpStatus.OK);
    }

    @Operation(summary = "Get transport options by place id")
    @ApiResponse(responseCode = "200", description = "Found transport options by place",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = TransportOptionDTO.class))))
    @GetMapping("byPlace/{placeId}")
    public List<TransportOptionDTO> getByPlace(@PathVariable Integer placeId) {
        return service.getByPlace(placeId);
    }

    @Operation(summary = "Create a transport option")
    @ApiResponse(responseCode = "201", description = "Transport option created",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TransportOptionDTO.class)))
    @PostMapping
    public ResponseEntity<TransportOptionDTO> add(@RequestBody TransportOptionDTO dto) {
        TransportOptionDTO saved = service.save(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a transport option")
    @ApiResponse(responseCode = "200", description = "Transport option updated",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TransportOptionDTO.class)))
    @PutMapping("{idTransport}")
    public ResponseEntity<TransportOptionDTO> update(@RequestBody TransportOptionDTO dto, @PathVariable Integer idTransport) {
        TransportOptionDTO updated = service.update(dto, idTransport);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(summary = "Delete a transport option")
    @ApiResponse(responseCode = "200", description = "Transport option deleted")
    @DeleteMapping("{idTransport}")
    public ResponseEntity<String> delete(@PathVariable Integer idTransport) {
        service.delete(idTransport);
        return new ResponseEntity<>("Deleted record", HttpStatus.OK);
    }
}
