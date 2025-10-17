package com.example.demo.controller;

import com.example.demo.dto.AddressDTO;
import com.example.demo.service.AddressService;
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
@RequestMapping("/api/addresses")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Addresses", description = "Provides methods for managing addresses")
public class AddressController {

    @Autowired
    private AddressService service;

    @Operation(summary = "Get all addresses")
    @ApiResponse(responseCode = "200", description = "Found addresses",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = AddressDTO.class))))
    @GetMapping
    public List<AddressDTO> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get address by ID")
    @ApiResponse(responseCode = "200", description = "Found address",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = AddressDTO.class)))
    @GetMapping("{id}")
    public ResponseEntity<AddressDTO> getById(@PathVariable Integer id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get addresses by postal code")
    @ApiResponse(responseCode = "200", description = "Found addresses",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = AddressDTO.class))))
    @GetMapping("postalCode/{postalCode}")
    public List<AddressDTO> getByPostalCode(@PathVariable String postalCode) {
        return service.getByPostalCode(postalCode);
    }

    @Operation(summary = "Register an address")
    @ApiResponse(responseCode = "201", description = "Created address",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = AddressDTO.class)))
    @PostMapping
    public ResponseEntity<AddressDTO> add(@RequestBody AddressDTO dto) {
        AddressDTO saved = service.save(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an address")
    @ApiResponse(responseCode = "200", description = "Updated address",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = AddressDTO.class)))
    @PutMapping("{id}")
    public ResponseEntity<AddressDTO> update(@RequestBody AddressDTO dto, @PathVariable Integer id) {
        AddressDTO updated = service.update(dto, id);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(summary = "Delete an address")
    @ApiResponse(responseCode = "200", description = "Address deleted")
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        service.delete(id);
        return new ResponseEntity<>("Deleted record", HttpStatus.OK);
    }
}
