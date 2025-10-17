package com.example.demo.controller;

import com.example.demo.dto.AppUserDTO;
import com.example.demo.service.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appusers")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "App Users", description = "Provides methods for managing application users")
public class AppUserController {

    @Autowired
    private AppUserService service;

    @Operation(summary = "Get all users")
    @ApiResponse(responseCode = "200", description = "Found users",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = AppUserDTO.class))))
    @GetMapping
    public List<AppUserDTO> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get user by ID")
    @ApiResponse(responseCode = "200", description = "Found user",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = AppUserDTO.class)))
    @GetMapping("{id}")
    public ResponseEntity<AppUserDTO> getById(@PathVariable Integer id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get user by username")
    @ApiResponse(responseCode = "200", description = "Found user",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = AppUserDTO.class)))
    @GetMapping("byUsername/{username}")
    public ResponseEntity<AppUserDTO> getByUsername(@PathVariable String username) {
        return new ResponseEntity<>(service.getByUsername(username), HttpStatus.OK);
    }

    @Operation(summary = "Create user")
    @ApiResponse(responseCode = "201", description = "User created",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = AppUserDTO.class)))
    @PostMapping
    public ResponseEntity<AppUserDTO> add(@Valid @RequestBody AppUserDTO dto) {
        AppUserDTO saved = service.save(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "Update user")
    @ApiResponse(responseCode = "200", description = "User updated",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = AppUserDTO.class)))
    @PutMapping("{id}")
    public ResponseEntity<AppUserDTO> update(@Valid @RequestBody AppUserDTO dto, @PathVariable Integer id) {
        AppUserDTO updated = service.update(dto, id);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(summary = "Delete user")
    @ApiResponse(responseCode = "200", description = "User deleted")
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        service.delete(id);
        return new ResponseEntity<>("Deleted record", HttpStatus.OK);
    }
}
