package com.example.demo.service;

import com.example.demo.dto.AppUserDTO;
import com.example.demo.model.AppUser;
import com.example.demo.model.Rol;
import com.example.demo.repository.AppUserRepository;
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
public class AppUserService {

    @Autowired
    private AppUserRepository repository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<AppUserDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AppUserDTO getById(Integer id) {
        AppUser user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
        return convertToDTO(user);
    }

    @Transactional(readOnly = true)
    public AppUserDTO getByUsername(String username) {
        AppUser user = repository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("User not found with username " + username);
        }
        return convertToDTO(user);
    }

    public AppUserDTO save(AppUserDTO dto) {
        AppUser entity = convertToEntity(dto);
        AppUser saved = repository.save(entity);
        return convertToDTO(saved);
    }

    public AppUserDTO update(AppUserDTO dto, Integer id) {
        AppUser existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));


        existing.setUsername(dto.getUsername());
        existing.setPassword(dto.getPassword());

        if (dto.getRoleId() != null) {
            Rol role = rolRepository.findById(dto.getRoleId())
                    .orElseThrow(() -> new EntityNotFoundException("Role not found with id " + dto.getRoleId()));
            existing.setRole(role);
        }

        AppUser updated = repository.save(existing);
        return convertToDTO(updated);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id " + id);
        }
        repository.deleteById(id);
    }


    private AppUserDTO convertToDTO(AppUser user) {
        AppUserDTO dto = modelMapper.map(user, AppUserDTO.class);
        dto.setRoleId(user.getRole().getId());
        return dto;
    }

    private AppUser convertToEntity(AppUserDTO dto) {
        AppUser user = modelMapper.map(dto, AppUser.class);
        if (dto.getRoleId() != null) {
            Rol role = rolRepository.findById(dto.getRoleId())
                    .orElseThrow(() -> new EntityNotFoundException("Role not found with id " + dto.getRoleId()));
            user.setRole(role);
        }
        return user;
    }
}
