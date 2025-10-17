package com.example.demo.service;

import com.example.demo.dto.RoleDTO;
import com.example.demo.model.Rol;
import com.example.demo.repository.RolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RolService {

    @Autowired
    private RolRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<RoleDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RoleDTO getById(Integer id) {
        Rol rol = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id " + id));
        return toDTO(rol);
    }

    @Transactional(readOnly = true)
    public RoleDTO getByName(String name) {
        Rol rol = repository.findByName(name);
        if (rol == null) {
            throw new EntityNotFoundException("Role not found with name " + name);
        }
        return toDTO(rol);
    }

    public RoleDTO save(RoleDTO dto) {
        Rol entity = toEntity(dto);
        Rol saved = repository.save(entity);
        return toDTO(saved);
    }

    public RoleDTO update(RoleDTO dto, Integer id) {
        Rol existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id " + id));

        existing.setName(dto.getName());

        Rol updated = repository.save(existing);
        return toDTO(updated);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Role not found with id " + id);
        }
        repository.deleteById(id);
    }
    private RoleDTO toDTO(Rol rol) {
        return modelMapper.map(rol, RoleDTO.class);
    }

    private Rol toEntity(RoleDTO dto) {
        return modelMapper.map(dto, Rol.class);
    }
}
