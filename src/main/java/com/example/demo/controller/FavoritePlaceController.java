package com.example.demo.controller;

import com.example.demo.dto.FavoritePlaceDTO;
import com.example.demo.service.FavoritePlaceService;
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
@RequestMapping("/api/favoriteplaces")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
@Tag(name = "Favorite Places", description = "Provides methods for managing users' favorite tourist places")
public class FavoritePlaceController {

    @Autowired
    private FavoritePlaceService service;

    @Operation(summary = "Get all favorite places")
    @ApiResponse(responseCode = "200", description = "Found favorites",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = FavoritePlaceDTO.class))))
    @GetMapping
    public List<FavoritePlaceDTO> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get favorite place by ID")
    @ApiResponse(responseCode = "200", description = "Found favorite",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = FavoritePlaceDTO.class)))
    @GetMapping("{id}")
    public ResponseEntity<FavoritePlaceDTO> getById(@PathVariable Integer id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get favorite places by user ID")
    @ApiResponse(responseCode = "200", description = "Found favorites by user",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = FavoritePlaceDTO.class))))
    @GetMapping("byUser/{userId}")
    public List<FavoritePlaceDTO> getByUser(@PathVariable Integer userId) {
        return service.getByUser(userId);
    }

    @Operation(summary = "Add favorite place")
    @ApiResponse(responseCode = "201", description = "Favorite created",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = FavoritePlaceDTO.class)))
    @PostMapping
    public ResponseEntity<FavoritePlaceDTO> add(@RequestBody FavoritePlaceDTO dto) {
        FavoritePlaceDTO saved = service.save(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete favorite place")
    @ApiResponse(responseCode = "200", description = "Favorite deleted")
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        service.delete(id);
        return new ResponseEntity<>("Deleted record", HttpStatus.OK);
    }
}
