package com.example.demo.controller;

import com.example.demo.dto.StoryDTO;
import com.example.demo.service.StoryService;
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
@RequestMapping("/api/stories")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Stories", description = "Provides methods for managing place stories")
public class StoryController {

    @Autowired
    private StoryService service;

    @Operation(summary = "Get all stories")
    @ApiResponse(responseCode = "200", description = "Found stories",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = StoryDTO.class))))
    @GetMapping
    public List<StoryDTO> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get story by ID")
    @ApiResponse(responseCode = "200", description = "Story found",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = StoryDTO.class)))
    @GetMapping("{idStory}")
    public ResponseEntity<StoryDTO> getById(@PathVariable Integer idStory) {
        return new ResponseEntity<>(service.getById(idStory), HttpStatus.OK);
    }

    @Operation(summary = "Get stories by place id")
    @ApiResponse(responseCode = "200", description = "Found stories by place",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = StoryDTO.class))))
    @GetMapping("byPlace/{placeId}")
    public List<StoryDTO> getByPlace(@PathVariable Integer placeId) {
        return service.getByPlace(placeId);
    }

    @Operation(summary = "Create a story")
    @ApiResponse(responseCode = "201", description = "Story created",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = StoryDTO.class)))
    @PostMapping
    public ResponseEntity<StoryDTO> add(@RequestBody StoryDTO dto) {
        StoryDTO saved = service.save(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a story")
    @ApiResponse(responseCode = "200", description = "Story updated",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = StoryDTO.class)))
    @PutMapping("{idStory}")
    public ResponseEntity<StoryDTO> update(@RequestBody StoryDTO dto, @PathVariable Integer idStory) {
        StoryDTO updated = service.update(dto, idStory);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(summary = "Delete a story")
    @ApiResponse(responseCode = "200", description = "Story deleted")
    @DeleteMapping("{idStory}")
    public ResponseEntity<String> delete(@PathVariable Integer idStory) {
        service.delete(idStory);
        return new ResponseEntity<>("Deleted record", HttpStatus.OK);
    }
}
