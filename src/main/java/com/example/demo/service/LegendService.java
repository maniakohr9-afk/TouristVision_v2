package com.example.demo.service;

import com.example.demo.dto.LegendDTO;
import com.example.demo.model.Legend;
import com.example.demo.model.TouristPlace;
import com.example.demo.repository.LegendRepository;
import com.example.demo.repository.TouristPlaceRepository; // asegúrate de tener este repo
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LegendService {

    @Autowired
    private LegendRepository repository;

    @Autowired
    private TouristPlaceRepository touristPlaceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<LegendDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LegendDTO getById(Integer id) {
        Legend legend = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Legend not found with id " + id));
        return toDTO(legend);
    }

    @Transactional(readOnly = true)
    public List<LegendDTO> getByPlace(Integer placeId) {
        return repository.findByPlace_Id(placeId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public LegendDTO save(LegendDTO dto) {
        Legend entity = toEntity(dto);
        Legend saved = repository.save(entity);
        return toDTO(saved);
    }

    public LegendDTO update(LegendDTO dto, Integer id) {
        Legend existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Legend not found with id " + id));

        existing.setTitle(dto.getTitle());
        existing.setStory(dto.getStory());
        existing.setOrigin(dto.getOrigin());

        if (dto.getPlaceId() != null) {
            TouristPlace place = touristPlaceRepository.findById(dto.getPlaceId())
                    .orElseThrow(() -> new EntityNotFoundException("TouristPlace not found with id " + dto.getPlaceId()));
            existing.setPlace(place);
        }

        Legend updated = repository.save(existing);
        return toDTO(updated);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Legend not found with id " + id);
        }
        repository.deleteById(id);
    }

    private LegendDTO toDTO(Legend l) {
        LegendDTO dto = modelMapper.map(l, LegendDTO.class);
        dto.setPlaceId(l.getPlace() != null ? l.getPlace().getId() : null);
        return dto;
    }

    private Legend toEntity(LegendDTO dto) {
        Legend l = modelMapper.map(dto, Legend.class);
        if (dto.getPlaceId() != null) {
            TouristPlace place = touristPlaceRepository.findById(dto.getPlaceId())
                    .orElseThrow(() -> new EntityNotFoundException("TouristPlace not found with id " + dto.getPlaceId()));
            l.setPlace(place);
        } else {
            l.setPlace(null);
        }
        return l;
    }
}
