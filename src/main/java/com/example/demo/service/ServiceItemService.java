package com.example.demo.service;

import com.example.demo.dto.ServiceDTO;
import com.example.demo.model.Address;
import com.example.demo.model.TouristPlace;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.ServiceRepository;
import com.example.demo.repository.TouristPlaceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Transactional
public class ServiceItemService {

    @Autowired
    private ServiceRepository repository;

    @Autowired
    private TouristPlaceRepository touristPlaceRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<ServiceDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ServiceDTO getById(Integer id) {
        com.example.demo.model.Service svc = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service not found with id " + id));
        return toDTO(svc);
    }

    @Transactional(readOnly = true)
    public List<ServiceDTO> getByPlace(Integer placeId) {
        return repository.findByPlace_Id(placeId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ServiceDTO save(ServiceDTO dto) {
        com.example.demo.model.Service entity = toEntity(dto);
        com.example.demo.model.Service saved = repository.save(entity);
        return toDTO(saved);
    }

    public ServiceDTO update(ServiceDTO dto, Integer id) {
        com.example.demo.model.Service existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service not found with id " + id));

        existing.setName(dto.getName());
        existing.setType(dto.getType());
        existing.setDescription(dto.getDescription());
        existing.setPriceRange(dto.getPriceRange());
        existing.setContactInfo(dto.getContactInfo());

        if (dto.getPlaceId() != null) {
            TouristPlace place = touristPlaceRepository.findById(dto.getPlaceId())
                    .orElseThrow(() -> new EntityNotFoundException("TouristPlace not found with id " + dto.getPlaceId()));
            existing.setPlace(place);
        } else {
            existing.setPlace(null);
        }

        if (dto.getAddressId() != null) {
            Address address = addressRepository.findById(dto.getAddressId())
                    .orElseThrow(() -> new EntityNotFoundException("Address not found with id " + dto.getAddressId()));
            existing.setAddress(address);
        } else {
            existing.setAddress(null);
        }

        com.example.demo.model.Service updated = repository.save(existing);
        return toDTO(updated);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Service not found with id " + id);
        }
        repository.deleteById(id);
    }

    private ServiceDTO toDTO(com.example.demo.model.Service s) {
        ServiceDTO dto = modelMapper.map(s, ServiceDTO.class);
        dto.setPlaceId(s.getPlace() != null ? s.getPlace().getId() : null);
        dto.setAddressId(s.getAddress() != null ? s.getAddress().getId() : null);
        return dto;
    }

    private com.example.demo.model.Service toEntity(ServiceDTO dto) {
        com.example.demo.model.Service s = modelMapper.map(dto, com.example.demo.model.Service.class);

        if (dto.getPlaceId() != null) {
            TouristPlace place = touristPlaceRepository.findById(dto.getPlaceId())
                    .orElseThrow(() -> new EntityNotFoundException("TouristPlace not found with id " + dto.getPlaceId()));
            s.setPlace(place);
        } else {
            s.setPlace(null);
        }

        if (dto.getAddressId() != null) {
            Address address = addressRepository.findById(dto.getAddressId())
                    .orElseThrow(() -> new EntityNotFoundException("Address not found with id " + dto.getAddressId()));
            s.setAddress(address);
        } else {
            s.setAddress(null);
        }

        return s;
    }
}
