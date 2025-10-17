package com.example.demo.service;

import com.example.demo.dto.StoryDTO;
import com.example.demo.model.Story;
import com.example.demo.model.TouristPlace;
import com.example.demo.repository.StoryRepository;
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
public class StoryService {

    @Autowired
    private StoryRepository repository;

    @Autowired
    private TouristPlaceRepository touristPlaceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<StoryDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StoryDTO getById(Integer id) {
        Story s = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Story not found with id " + id));
        return toDTO(s);
    }

    @Transactional(readOnly = true)
    public List<StoryDTO> getByPlace(Integer placeId) {

        return repository.findByPlace_Id(placeId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public StoryDTO save(StoryDTO dto) {
        Story entity = toEntity(dto);
        Story saved = repository.save(entity);
        return toDTO(saved);
    }

    public StoryDTO update(StoryDTO dto, Integer id) {
        Story existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Story not found with id " + id));

        existing.setHistory(dto.getHistory());
        existing.setTraditions(dto.getTraditions());

        if (dto.getPlaceId() != null) {
            TouristPlace place = touristPlaceRepository.findById(dto.getPlaceId())
                    .orElseThrow(() -> new EntityNotFoundException("TouristPlace not found with id " + dto.getPlaceId()));
            existing.setPlace(place);
        } else {
            existing.setPlace(null);
        }

        Story updated = repository.save(existing);
        return toDTO(updated);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Story not found with id " + id);
        }
        repository.deleteById(id);
    }


    private StoryDTO toDTO(Story s) {
        StoryDTO dto = modelMapper.map(s, StoryDTO.class);
        dto.setPlaceId(s.getPlace() != null ? s.getPlace().getId() : null);
        return dto;
    }

    private Story toEntity(StoryDTO dto) {
        Story s = modelMapper.map(dto, Story.class);
        if (dto.getPlaceId() != null) {
            TouristPlace place = touristPlaceRepository.findById(dto.getPlaceId())
                    .orElseThrow(() -> new EntityNotFoundException("TouristPlace not found with id " + dto.getPlaceId()));
            s.setPlace(place);
        } else {
            s.setPlace(null);
        }
        return s;
    }
}
