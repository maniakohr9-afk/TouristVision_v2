package com.example.demo.service;

import com.example.demo.dto.AddressDTO;
import com.example.demo.model.Address;
import com.example.demo.repository.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AddressService {

    @Autowired
    private AddressRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<AddressDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AddressDTO getById(Integer id) {
        Address address = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id " + id));
        return convertToDTO(address);
    }

    @Transactional(readOnly = true)
    public List<AddressDTO> getByPostalCode(String postalCode) {
        return repository.findByPostalCode(postalCode)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AddressDTO save(AddressDTO dto) {
        Address entity = convertToEntity(dto);
        Address saved = repository.save(entity);
        return convertToDTO(saved);
    }

    public AddressDTO update(AddressDTO dto, Integer id) {
        Address existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id " + id));

        existing.setStreet(dto.getStreet());
        existing.setNeighborhood(dto.getNeighborhood());
        existing.setPostalCode(dto.getPostalCode());
        existing.setLatitude(dto.getLatitude());
        existing.setLongitude(dto.getLongitude());
        existing.setReference(dto.getReference());

        Address updated = repository.save(existing);
        return convertToDTO(updated);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Address not found with id " + id);
        }
        repository.deleteById(id);
    }

    private AddressDTO convertToDTO(Address address) {
        return modelMapper.map(address, AddressDTO.class);
    }

    private Address convertToEntity(AddressDTO dto) {
        return modelMapper.map(dto, Address.class);
    }
}
