package com.example.demo.controller;

import com.example.demo.dto.EventDTO;
import com.example.demo.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Events", description = "Provides methods for managing events")
public class EventController {

    @Autowired
    private EventService service;

    @Operation(summary = "Get all events")
    @ApiResponse(responseCode = "200", description = "Found events",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = EventDTO.class))))
    @GetMapping
    public List<EventDTO> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get event by ID")
    @ApiResponse(responseCode = "200", description = "Event found",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = EventDTO.class)))
    @GetMapping("{idEvent}")
    public ResponseEntity<EventDTO> getById(@PathVariable Integer idEvent) {
        return new ResponseEntity<>(service.getById(idEvent), HttpStatus.OK);
    }

    @Operation(summary = "Get events by place id")
    @ApiResponse(responseCode = "200", description = "Found events by place",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = EventDTO.class))))
    @GetMapping("byPlace/{placeId}")
    public List<EventDTO> getByPlace(@PathVariable Integer placeId) {
        return service.getByPlace(placeId);
    }

    @Operation(summary = "Get upcoming events (eventDate >= now)")
    @ApiResponse(responseCode = "200", description = "Found upcoming events",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = EventDTO.class))))
    @GetMapping("upcoming")
    public List<EventDTO> getUpcoming() {
        return service.getUpcoming();
    }

    @Operation(summary = "Get events by date range [start, end] (ISO-8601)")
    @ApiResponse(responseCode = "200", description = "Found events in date range",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = EventDTO.class))))
    @GetMapping("byDateRange")
public List<EventDTO> getByDateRange(
        @RequestParam("start") String start,
        @RequestParam("end") String end) {
    return service.getByDateRange(start, end);
}

    @Operation(summary = "Create an event")
    @ApiResponse(responseCode = "201", description = "Event created",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = EventDTO.class)))
    @PostMapping
    public ResponseEntity<EventDTO> add(@RequestBody EventDTO dto) {
        EventDTO saved = service.save(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an event")
    @ApiResponse(responseCode = "200", description = "Event updated",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = EventDTO.class)))
    @PutMapping("{idEvent}")
    public ResponseEntity<EventDTO> update(@RequestBody EventDTO dto, @PathVariable Integer idEvent) {
        EventDTO updated = service.update(dto, idEvent);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(summary = "Delete an event")
    @ApiResponse(responseCode = "200", description = "Event deleted")
    @DeleteMapping("{idEvent}")
    public ResponseEntity<String> delete(@PathVariable Integer idEvent) {
        service.delete(idEvent);
        return new ResponseEntity<>("Deleted record", HttpStatus.OK);
    }
}
