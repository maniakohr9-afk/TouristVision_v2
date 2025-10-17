package com.example.demo.controller;

import com.example.demo.dto.RoleDTO;
import com.example.demo.service.RolService;
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
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Roles", description = "Provides methods for managing user roles")
public class RolController {

    @Autowired
    private RolService service;

    @Operation(summary = "Get all roles")
    @ApiResponse(responseCode = "200", description = "Found roles",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = RoleDTO.class))))
    @GetMapping
    public List<RoleDTO> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get role by ID")
    @ApiResponse(responseCode = "200", description = "Role found",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = RoleDTO.class)))
    @GetMapping("{idRole}")
    public ResponseEntity<RoleDTO> getById(@PathVariable Integer idRole) {
        return new ResponseEntity<>(service.getById(idRole), HttpStatus.OK);
    }

    @Operation(summary = "Get role by name")
    @ApiResponse(responseCode = "200", description = "Role found",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = RoleDTO.class)))
    @GetMapping("byName/{name}")
    public ResponseEntity<RoleDTO> getByName(@PathVariable String name) {
        return new ResponseEntity<>(service.getByName(name), HttpStatus.OK);
    }

    @Operation(summary = "Create a role")
    @ApiResponse(responseCode = "201", description = "Role created",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = RoleDTO.class)))
    @PostMapping
    public ResponseEntity<RoleDTO> add(@Valid @RequestBody RoleDTO dto) {
        RoleDTO saved = service.save(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a role")
    @ApiResponse(responseCode = "200", description = "Role updated",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = RoleDTO.class)))
    @PutMapping("{idRole}")
    public ResponseEntity<RoleDTO> update(@Valid @RequestBody RoleDTO dto, @PathVariable Integer idRole) {
        RoleDTO updated = service.update(dto, idRole);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(summary = "Delete a role")
    @ApiResponse(responseCode = "200", description = "Role deleted")
    @DeleteMapping("{idRole}")
    public ResponseEntity<String> delete(@PathVariable Integer idRole) {
        service.delete(idRole);
        return new ResponseEntity<>("Deleted record", HttpStatus.OK);
    }
}
