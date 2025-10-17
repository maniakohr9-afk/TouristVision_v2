package com.example.demo.service;

import com.example.demo.dto.GastronomyDTO;
import com.example.demo.model.Gastronomy;
import com.example.demo.model.TouristPlace;
import com.example.demo.repository.GastronomyRepository;
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
public class GastronomyService {

    @Autowired
    private GastronomyRepository repository;

    @Autowired
    private TouristPlaceRepository touristPlaceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<GastronomyDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GastronomyDTO getById(Integer id) {
        Gastronomy dish = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dish not found with id " + id));
        return toDTO(dish);
    }

    @Transactional(readOnly = true)
    public List<GastronomyDTO> getByPlace(Integer placeId) {
        return repository.findByPlaceId(placeId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public GastronomyDTO save(GastronomyDTO dto) {
        Gastronomy entity = toEntity(dto);
        Gastronomy saved = repository.save(entity);
        return toDTO(saved);
    }

    public GastronomyDTO update(GastronomyDTO dto, Integer id) {
        Gastronomy existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dish not found with id " + id));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());

        if (dto.getPlaceId() != null) {
            TouristPlace place = touristPlaceRepository.findById(dto.getPlaceId())
                    .orElseThrow(() -> new EntityNotFoundException("TouristPlace not found with id " + dto.getPlaceId()));
            existing.setPlace(place);
        }

        Gastronomy updated = repository.save(existing);
        return toDTO(updated);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Dish not found with id " + id);
        }
        repository.deleteById(id);
    }

    private GastronomyDTO toDTO(Gastronomy g) {
        GastronomyDTO dto = modelMapper.map(g, GastronomyDTO.class);
        dto.setPlaceId(g.getPlace() != null ? g.getPlace().getId() : null);
        return dto;
    }

    private Gastronomy toEntity(GastronomyDTO dto) {
        Gastronomy g = modelMapper.map(dto, Gastronomy.class);
        if (dto.getPlaceId() != null) {
            TouristPlace place = touristPlaceRepository.findById(dto.getPlaceId())
                    .orElseThrow(() -> new EntityNotFoundException("TouristPlace not found with id " + dto.getPlaceId()));
            g.setPlace(place);
        }
        return g;
    }
}
