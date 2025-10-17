package com.example.demo.service;

import com.example.demo.dto.TouristPlaceDTO;
import com.example.demo.model.Address;
import com.example.demo.model.Municipality;
import com.example.demo.model.TouristPlace;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.MunicipalityRepository;
import com.example.demo.repository.TouristPlaceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TouristPlaceService {

    @Autowired
    private TouristPlaceRepository repository;

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<TouristPlaceDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TouristPlaceDTO getById(Integer id) {
        TouristPlace place = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TouristPlace not found with id " + id));
        return toDTO(place);
    }

    @Transactional(readOnly = true)
    public List<TouristPlaceDTO> getByMunicipality(Integer municipalityId) {

        return repository.findByMunicipality(municipalityId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public TouristPlaceDTO save(TouristPlaceDTO dto) {
        TouristPlace entity = toEntity(dto);
        TouristPlace saved = repository.save(entity);
        return toDTO(saved);
    }

    public TouristPlaceDTO update(TouristPlaceDTO dto, Integer id) {
        TouristPlace existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TouristPlace not found with id " + id));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());

        if (dto.getMunicipalityId() != null) {
            Municipality m = municipalityRepository.findById(dto.getMunicipalityId())
                    .orElseThrow(() -> new EntityNotFoundException("Municipality not found with id " + dto.getMunicipalityId()));
            existing.setMunicipality(m);
        }

        if (dto.getAddressId() != null) {
            Address a = addressRepository.findById(dto.getAddressId())
                    .orElseThrow(() -> new EntityNotFoundException("Address not found with id " + dto.getAddressId()));
            existing.setAddress(a);
        } else {
            existing.setAddress(null);
        }

        TouristPlace updated = repository.save(existing);
        return toDTO(updated);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("TouristPlace not found with id " + id);
        }
        repository.deleteById(id);
    }


    private TouristPlaceDTO toDTO(TouristPlace p) {
        TouristPlaceDTO dto = modelMapper.map(p, TouristPlaceDTO.class);
        dto.setMunicipalityId(p.getMunicipality() != null ? p.getMunicipality().getId() : null);
        dto.setAddressId(p.getAddress() != null ? p.getAddress().getId() : null);
        return dto;
    }

    private TouristPlace toEntity(TouristPlaceDTO dto) {
        TouristPlace p = modelMapper.map(dto, TouristPlace.class);

        if (dto.getMunicipalityId() != null) {
            Municipality m = municipalityRepository.findById(dto.getMunicipalityId())
                    .orElseThrow(() -> new EntityNotFoundException("Municipality not found with id " + dto.getMunicipalityId()));
            p.setMunicipality(m);
        }

        if (dto.getAddressId() != null) {
            Address a = addressRepository.findById(dto.getAddressId())
                    .orElseThrow(() -> new EntityNotFoundException("Address not found with id " + dto.getAddressId()));
            p.setAddress(a);
        } else {
            p.setAddress(null);
        }

        return p;
    }
}
