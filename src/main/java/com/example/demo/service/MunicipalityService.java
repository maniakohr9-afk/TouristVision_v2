package com.example.demo.service;

import com.example.demo.dto.MunicipalityDTO;
import com.example.demo.model.Municipality;
import com.example.demo.model.State;
import com.example.demo.repository.MunicipalityRepository;
import com.example.demo.repository.StateRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MunicipalityService {

    @Autowired
    private MunicipalityRepository repository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<MunicipalityDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MunicipalityDTO getById(Integer id) {
        Municipality m = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Municipality not found with id " + id));
        return toDTO(m);
    }

    @Transactional(readOnly = true)
    public List<MunicipalityDTO> getByState(Integer stateId) {
        return repository.findByState(stateId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public MunicipalityDTO save(MunicipalityDTO dto) {
        Municipality entity = toEntity(dto);
        Municipality saved = repository.save(entity);
        return toDTO(saved);
    }

    public MunicipalityDTO update(MunicipalityDTO dto, Integer id) {
        Municipality existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Municipality not found with id " + id));

        existing.setName(dto.getName());
        if (dto.getStateId() != null) {
            State st = stateRepository.findById(dto.getStateId())
                    .orElseThrow(() -> new EntityNotFoundException("State not found with id " + dto.getStateId()));
            existing.setState(st);
        }

        Municipality updated = repository.save(existing);
        return toDTO(updated);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Municipality not found with id " + id);
        }
        repository.deleteById(id);
    }

    private MunicipalityDTO toDTO(Municipality m) {
        MunicipalityDTO dto = modelMapper.map(m, MunicipalityDTO.class);
        dto.setStateId(m.getState() != null ? m.getState().getId() : null);
        return dto;
    }

    private Municipality toEntity(MunicipalityDTO dto) {
        Municipality m = modelMapper.map(dto, Municipality.class);
        if (dto.getStateId() != null) {
            State st = stateRepository.findById(dto.getStateId())
                    .orElseThrow(() -> new EntityNotFoundException("State not found with id " + dto.getStateId()));
            m.setState(st);
        } else {
            m.setState(null);
        }
        return m;
    }
}
