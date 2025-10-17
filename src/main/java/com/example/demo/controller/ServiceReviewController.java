package com.example.demo.controller;

import com.example.demo.dto.ServiceReviewDTO;
import com.example.demo.service.ServiceReviewService;
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
@RequestMapping("/api/service-reviews")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Service Reviews", description = "Provides methods for managing reviews of services")
public class ServiceReviewController {

    @Autowired
    private ServiceReviewService service;

    @Operation(summary = "Get all service reviews")
    @ApiResponse(responseCode = "200", description = "Found reviews",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ServiceReviewDTO.class))))
    @GetMapping
    public List<ServiceReviewDTO> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get service review by ID")
    @ApiResponse(responseCode = "200", description = "Review found",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ServiceReviewDTO.class)))
    @GetMapping("{idReview}")
    public ResponseEntity<ServiceReviewDTO> getById(@PathVariable Integer idReview) {
        return new ResponseEntity<>(service.getById(idReview), HttpStatus.OK);
    }

    @Operation(summary = "Get reviews by service ID")
    @ApiResponse(responseCode = "200", description = "Found reviews by service",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ServiceReviewDTO.class))))
    @GetMapping("byService/{serviceId}")
    public List<ServiceReviewDTO> getByService(@PathVariable Integer serviceId) {
        return service.getByService(serviceId);
    }

    @Operation(summary = "Create a service review")
    @ApiResponse(responseCode = "201", description = "Review created",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ServiceReviewDTO.class)))
    @PostMapping
    public ResponseEntity<ServiceReviewDTO> add(@RequestBody ServiceReviewDTO dto) {
        ServiceReviewDTO saved = service.save(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a service review")
    @ApiResponse(responseCode = "200", description = "Review updated",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ServiceReviewDTO.class)))
    @PutMapping("{idReview}")
    public ResponseEntity<ServiceReviewDTO> update(@RequestBody ServiceReviewDTO dto, @PathVariable Integer idReview) {
        ServiceReviewDTO updated = service.update(dto, idReview);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(summary = "Delete a service review")
    @ApiResponse(responseCode = "200", description = "Review deleted")
    @DeleteMapping("{idReview}")
    public ResponseEntity<String> delete(@PathVariable Integer idReview) {
        service.delete(idReview);
        return new ResponseEntity<>("Deleted record", HttpStatus.OK);
    }
}
