package com.example.demo.controller;

import com.example.demo.dto.ReviewDTO;
import com.example.demo.service.ReviewService;
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
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Reviews", description = "Provides methods for managing reviews")
public class ReviewController {

    @Autowired
    private ReviewService service;

    @Operation(summary = "Get all reviews")
    @ApiResponse(responseCode = "200", description = "Found reviews",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ReviewDTO.class))))
    @GetMapping
    public List<ReviewDTO> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get review by ID")
    @ApiResponse(responseCode = "200", description = "Review found",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ReviewDTO.class)))
    @GetMapping("{idReview}")
    public ResponseEntity<ReviewDTO> getById(@PathVariable Integer idReview) {
        return new ResponseEntity<>(service.getById(idReview), HttpStatus.OK);
    }

    @Operation(summary = "Get reviews by place id")
    @ApiResponse(responseCode = "200", description = "Found reviews by place",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ReviewDTO.class))))
    @GetMapping("byPlace/{placeId}")
    public List<ReviewDTO> getByPlace(@PathVariable Integer placeId) {
        return service.getByPlace(placeId);
    }

    @Operation(summary = "Create a review")
    @ApiResponse(responseCode = "201", description = "Review created",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ReviewDTO.class)))
    @PostMapping
    public ResponseEntity<ReviewDTO> add(@RequestBody ReviewDTO dto) {
        ReviewDTO saved = service.save(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a review")
    @ApiResponse(responseCode = "200", description = "Review updated",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ReviewDTO.class)))
    @PutMapping("{idReview}")
    public ResponseEntity<ReviewDTO> update(@RequestBody ReviewDTO dto, @PathVariable Integer idReview) {
        ReviewDTO updated = service.update(dto, idReview);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(summary = "Delete a review")
    @ApiResponse(responseCode = "200", description = "Review deleted")
    @DeleteMapping("{idReview}")
    public ResponseEntity<String> delete(@PathVariable Integer idReview) {
        service.delete(idReview);
        return new ResponseEntity<>("Deleted record", HttpStatus.OK);
    }
}
