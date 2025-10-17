package com.example.demo.controller;

import com.example.demo.dto.ReviewImageDTO;
import com.example.demo.service.ReviewImageService;
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
@RequestMapping("/api/review-images")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Review Images", description = "Provides methods for managing images inside reviews")
public class ReviewImageController {

    @Autowired
    private ReviewImageService service;

    @Operation(summary = "Get all review images")
    @ApiResponse(responseCode = "200", description = "Found images",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ReviewImageDTO.class))))
    @GetMapping
    public List<ReviewImageDTO> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get review image by ID")
    @ApiResponse(responseCode = "200", description = "Image found",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ReviewImageDTO.class)))
    @GetMapping("{idImage}")
    public ResponseEntity<ReviewImageDTO> getById(@PathVariable Integer idImage) {
        return new ResponseEntity<>(service.getById(idImage), HttpStatus.OK);
    }

    @Operation(summary = "Get review images by review ID")
    @ApiResponse(responseCode = "200", description = "Images found by review",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ReviewImageDTO.class))))
    @GetMapping("byReview/{reviewId}")
    public List<ReviewImageDTO> getByReview(@PathVariable Integer reviewId) {
        return service.getByReview(reviewId);
    }

    @Operation(summary = "Create a review image")
    @ApiResponse(responseCode = "201", description = "Image created",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ReviewImageDTO.class)))
    @PostMapping
    public ResponseEntity<ReviewImageDTO> add(@RequestBody ReviewImageDTO dto) {
        ReviewImageDTO saved = service.save(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a review image")
    @ApiResponse(responseCode = "200", description = "Image updated",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ReviewImageDTO.class)))
    @PutMapping("{idImage}")
    public ResponseEntity<ReviewImageDTO> update(@RequestBody ReviewImageDTO dto, @PathVariable Integer idImage) {
        ReviewImageDTO updated = service.update(dto, idImage);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(summary = "Delete a review image")
    @ApiResponse(responseCode = "200", description = "Image deleted")
    @DeleteMapping("{idImage}")
    public ResponseEntity<String> delete(@PathVariable Integer idImage) {
        service.delete(idImage);
        return new ResponseEntity<>("Deleted record", HttpStatus.OK);
    }
}
