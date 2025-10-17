package com.example.demo.service;

import com.example.demo.dto.StateDTO;
import com.example.demo.model.State;
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
public class StateService {

    @Autowired
    private StateRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<StateDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StateDTO getById(Integer id) {
        State state = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("State not found with id " + id));
        return toDTO(state);
    }

    @Transactional(readOnly = true)
    public List<StateDTO> searchByName(String name) {
        return repository.findByNameLike(name)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public StateDTO save(StateDTO dto) {
        State entity = toEntity(dto);
        State saved = repository.save(entity);
        return toDTO(saved);
    }

    public StateDTO update(StateDTO dto, Integer id) {
        State existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("State not found with id " + id));

        existing.setName(dto.getName());

        State updated = repository.save(existing);
        return toDTO(updated);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("State not found with id " + id);
        }
        repository.deleteById(id);
    }


    private StateDTO toDTO(State state) {
        return modelMapper.map(state, StateDTO.class);
    }

    private State toEntity(StateDTO dto) {
        return modelMapper.map(dto, State.class);
    }
}
