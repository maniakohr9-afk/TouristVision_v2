package com.example.demo.controller;

import com.example.demo.dto.LegendDTO;
import com.example.demo.service.LegendService;
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
@RequestMapping("/api/legends")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Legends", description = "Provides methods for managing legends")
public class LegendController {

    @Autowired
    private LegendService service;

    @Operation(summary = "Get all legends")
    @ApiResponse(responseCode = "200", description = "Found legends",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = LegendDTO.class))))
    @GetMapping
    public List<LegendDTO> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get legend by ID")
    @ApiResponse(responseCode = "200", description = "Legend found",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = LegendDTO.class)))
    @GetMapping("{idLegend}")
    public ResponseEntity<LegendDTO> getById(@PathVariable Integer idLegend) {
        return new ResponseEntity<>(service.getById(idLegend), HttpStatus.OK);
    }

    @Operation(summary = "Get legends by place id")
    @ApiResponse(responseCode = "200", description = "Found legends by place",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = LegendDTO.class))))
    @GetMapping("byPlace/{placeId}")
    public List<LegendDTO> getByPlace(@PathVariable Integer placeId) {
        return service.getByPlace(placeId);
    }

    @Operation(summary = "Create a legend")
    @ApiResponse(responseCode = "201", description = "Legend created",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = LegendDTO.class)))
    @PostMapping
    public ResponseEntity<LegendDTO> add(@RequestBody LegendDTO dto) {
        LegendDTO saved = service.save(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a legend")
    @ApiResponse(responseCode = "200", description = "Legend updated",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = LegendDTO.class)))
    @PutMapping("{idLegend}")
    public ResponseEntity<LegendDTO> update(@RequestBody LegendDTO dto, @PathVariable Integer idLegend) {
        LegendDTO updated = service.update(dto, idLegend);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(summary = "Delete a legend")
    @ApiResponse(responseCode = "200", description = "Legend deleted")
    @DeleteMapping("{idLegend}")
    public ResponseEntity<String> delete(@PathVariable Integer idLegend) {
        service.delete(idLegend);
        return new ResponseEntity<>("Deleted record", HttpStatus.OK);
    }
}
