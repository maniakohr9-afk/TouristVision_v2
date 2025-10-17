package com.example.demo.service;

import com.example.demo.dto.TransportOptionDTO;
import com.example.demo.model.TouristPlace;
import com.example.demo.model.TransportOption;
import com.example.demo.repository.TouristPlaceRepository;
import com.example.demo.repository.TransportOptionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransportOptionService {

    @Autowired
    private TransportOptionRepository repository;

    @Autowired
    private TouristPlaceRepository touristPlaceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<TransportOptionDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TransportOptionDTO getById(Integer id) {
        TransportOption opt = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TransportOption not found with id " + id));
        return toDTO(opt);
    }

    @Transactional(readOnly = true)
    public List<TransportOptionDTO> getByPlace(Integer placeId) {
        return repository.findByPlace_Id(placeId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public TransportOptionDTO save(TransportOptionDTO dto) {
        TransportOption entity = toEntity(dto);
        TransportOption saved = repository.save(entity);
        return toDTO(saved);
    }

    public TransportOptionDTO update(TransportOptionDTO dto, Integer id) {
        TransportOption existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TransportOption not found with id " + id));

        existing.setType(dto.getType());
        existing.setDescription(dto.getDescription());

        if (dto.getPlaceId() != null) {
            TouristPlace place = touristPlaceRepository.findById(dto.getPlaceId())
                    .orElseThrow(() -> new EntityNotFoundException("TouristPlace not found with id " + dto.getPlaceId()));
            existing.setPlace(place);
        } else {
            existing.setPlace(null);
        }

        TransportOption updated = repository.save(existing);
        return toDTO(updated);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("TransportOption not found with id " + id);
        }
        repository.deleteById(id);
    }


    private TransportOptionDTO toDTO(TransportOption opt) {
        TransportOptionDTO dto = modelMapper.map(opt, TransportOptionDTO.class);
        dto.setPlaceId(opt.getPlace() != null ? opt.getPlace().getId() : null);
        return dto;
    }

    private TransportOption toEntity(TransportOptionDTO dto) {
        TransportOption opt = modelMapper.map(dto, TransportOption.class);
        if (dto.getPlaceId() != null) {
            TouristPlace place = touristPlaceRepository.findById(dto.getPlaceId())
                    .orElseThrow(() -> new EntityNotFoundException("TouristPlace not found with id " + dto.getPlaceId()));
            opt.setPlace(place);
        } else {
            opt.setPlace(null);
        }
        return opt;
    }
}
