package com.example.demo.controller;

import com.example.demo.dto.ServiceDTO;
import com.example.demo.service.ServiceItemService;
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
@RequestMapping("/api/services")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Services", description = "Provides methods for managing services offered in tourist places")
public class ServiceController {

    @Autowired
    private ServiceItemService service;

    @Operation(summary = "Get all services")
    @ApiResponse(responseCode = "200", description = "Found services",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ServiceDTO.class))))
    @GetMapping
    public List<ServiceDTO> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get service by ID")
    @ApiResponse(responseCode = "200", description = "Service found",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ServiceDTO.class)))
    @GetMapping("{idService}")
    public ResponseEntity<ServiceDTO> getById(@PathVariable Integer idService) {
        return new ResponseEntity<>(service.getById(idService), HttpStatus.OK);
    }

    @Operation(summary = "Get services by place id")
    @ApiResponse(responseCode = "200", description = "Found services by place",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ServiceDTO.class))))
    @GetMapping("byPlace/{placeId}")
    public List<ServiceDTO> getByPlace(@PathVariable Integer placeId) {
        return service.getByPlace(placeId);
    }

    @Operation(summary = "Create a service")
    @ApiResponse(responseCode = "201", description = "Service created",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ServiceDTO.class)))
    @PostMapping
    public ResponseEntity<ServiceDTO> add(@RequestBody ServiceDTO dto) {
        ServiceDTO saved = service.save(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a service")
    @ApiResponse(responseCode = "200", description = "Service updated",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ServiceDTO.class)))
    @PutMapping("{idService}")
    public ResponseEntity<ServiceDTO> update(@RequestBody ServiceDTO dto, @PathVariable Integer idService) {
        ServiceDTO updated = service.update(dto, idService);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(summary = "Delete a service")
    @ApiResponse(responseCode = "200", description = "Service deleted")
    @DeleteMapping("{idService}")
    public ResponseEntity<String> delete(@PathVariable Integer idService) {
        service.delete(idService);
        return new ResponseEntity<>("Deleted record", HttpStatus.OK);
    }
}
