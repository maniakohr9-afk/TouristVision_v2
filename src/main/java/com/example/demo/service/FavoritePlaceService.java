package com.example.demo.service;

import com.example.demo.dto.FavoritePlaceDTO;
import com.example.demo.model.AppUser;
import com.example.demo.model.FavoritePlace;
import com.example.demo.model.TouristPlace;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.repository.FavoritePlaceRepository;
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
public class FavoritePlaceService {

    @Autowired
    private FavoritePlaceRepository repository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private TouristPlaceRepository touristPlaceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<FavoritePlaceDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FavoritePlaceDTO getById(Integer id) {
        FavoritePlace fav = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FavoritePlace not found with id " + id));
        return toDTO(fav);
    }

    @Transactional(readOnly = true)
    public List<FavoritePlaceDTO> getByUser(Integer userId) {
        return repository.findByUserId(userId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public FavoritePlaceDTO save(FavoritePlaceDTO dto) {
        FavoritePlace entity = toEntity(dto);
        FavoritePlace saved = repository.save(entity);
        return toDTO(saved);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("FavoritePlace not found with id " + id);
        }
        repository.deleteById(id);
    }

    private FavoritePlaceDTO toDTO(FavoritePlace fav) {
        FavoritePlaceDTO dto = modelMapper.map(fav, FavoritePlaceDTO.class);
        dto.setUserId(fav.getUser() != null ? fav.getUser().getId() : null);
        dto.setPlaceId(fav.getPlace() != null ? fav.getPlace().getId() : null);
        return dto;
    }

    private FavoritePlace toEntity(FavoritePlaceDTO dto) {
        FavoritePlace fav = new FavoritePlace();

        if (dto.getId() != null) {
            fav.setId(dto.getId());
        }

        if (dto.getUserId() != null) {
            AppUser user = appUserRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id " + dto.getUserId()));
            fav.setUser(user);
        }

        if (dto.getPlaceId() != null) {
            TouristPlace place = touristPlaceRepository.findById(dto.getPlaceId())
                    .orElseThrow(() -> new EntityNotFoundException("Place not found with id " + dto.getPlaceId()));
            fav.setPlace(place);
        }

        return fav;
    }
}
