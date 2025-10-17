package com.example.demo.controller;

import com.example.demo.dto.TextAnalysisDTO;
import com.example.demo.service.TextAnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/text-analysis")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Text Analysis", description = "Provides methods for managing text analysis results of reviews")
public class TextAnalysisController {

    @Autowired
    private TextAnalysisService service;

    @Operation(summary = "Get text analysis by ID")
    @ApiResponse(responseCode = "200", description = "Found text analysis",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TextAnalysisDTO.class)))
    @GetMapping("{id}")
    public ResponseEntity<TextAnalysisDTO> getById(@PathVariable Integer id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get text analysis by review ID")
    @ApiResponse(responseCode = "200", description = "Found text analysis",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TextAnalysisDTO.class)))
    @GetMapping("byReview/{reviewId}")
    public ResponseEntity<TextAnalysisDTO> getByReview(@PathVariable Integer reviewId) {
        return new ResponseEntity<>(service.getByReview(reviewId), HttpStatus.OK);
    }

    @Operation(summary = "Create text analysis")
    @ApiResponse(responseCode = "201", description = "Text analysis created",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TextAnalysisDTO.class)))
    @PostMapping
    public ResponseEntity<TextAnalysisDTO> add(@RequestBody TextAnalysisDTO dto) {
        TextAnalysisDTO saved = service.save(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "Update text analysis")
    @ApiResponse(responseCode = "200", description = "Text analysis updated",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TextAnalysisDTO.class)))
    @PutMapping("{id}")
    public ResponseEntity<TextAnalysisDTO> update(@RequestBody TextAnalysisDTO dto, @PathVariable Integer id) {
        TextAnalysisDTO updated = service.update(dto, id);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(summary = "Delete text analysis")
    @ApiResponse(responseCode = "200", description = "Text analysis deleted")
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        service.delete(id);
        return new ResponseEntity<>("Deleted record", HttpStatus.OK);
    }
}
