package com.example.demo.controller;

import com.example.demo.dto.ImageAnalysisDTO;
import com.example.demo.service.ImageAnalysisService;
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
@RequestMapping("/api/image-analysis")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Image Analysis", description = "Provides methods for managing image analysis results")
public class ImageAnalysisController {

    @Autowired
    private ImageAnalysisService service;

    @Operation(summary = "Get image analysis by ID")
    @ApiResponse(responseCode = "200", description = "Found analysis",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ImageAnalysisDTO.class)))
    @GetMapping("{id}")
    public ResponseEntity<ImageAnalysisDTO> getById(@PathVariable Integer id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get image analysis by image id")
    @ApiResponse(responseCode = "200", description = "Found analysis",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ImageAnalysisDTO.class)))
    @GetMapping("byImage/{imageId}")
    public ResponseEntity<ImageAnalysisDTO> getByImage(@PathVariable Integer imageId) {
        return new ResponseEntity<>(service.getByImage(imageId), HttpStatus.OK);
    }

    @Operation(summary = "Create image analysis")
    @ApiResponse(responseCode = "201", description = "Created analysis",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ImageAnalysisDTO.class)))
    @PostMapping
    public ResponseEntity<ImageAnalysisDTO> add(@RequestBody ImageAnalysisDTO dto) {
        ImageAnalysisDTO saved = service.save(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "Update image analysis")
    @ApiResponse(responseCode = "200", description = "Updated analysis",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ImageAnalysisDTO.class)))
    @PutMapping("{id}")
    public ResponseEntity<ImageAnalysisDTO> update(@RequestBody ImageAnalysisDTO dto, @PathVariable Integer id) {
        ImageAnalysisDTO updated = service.update(dto, id);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(summary = "Delete image analysis")
    @ApiResponse(responseCode = "200", description = "Deleted analysis")
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        service.delete(id);
        return new ResponseEntity<>("Deleted record", HttpStatus.OK);
    }
}
