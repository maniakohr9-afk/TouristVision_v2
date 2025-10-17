package com.example.demo.service;

import com.example.demo.dto.ReviewDTO;
import com.example.demo.model.AppUser;
import com.example.demo.model.Review;
import com.example.demo.model.TouristPlace;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.repository.ReviewRepository;
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
public class ReviewService {

    @Autowired
    private ReviewRepository repository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private TouristPlaceRepository touristPlaceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<ReviewDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReviewDTO getById(Integer id) {
        Review r = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id " + id));
        return toDTO(r);
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> getByPlace(Integer placeId) {
        return repository.findByPlace(placeId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ReviewDTO save(ReviewDTO dto) {
        Review entity = toEntity(dto);
        Review saved = repository.save(entity);
        return toDTO(saved);
    }

    public ReviewDTO update(ReviewDTO dto, Integer id) {
        Review existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id " + id));

        existing.setTitle(dto.getTitle());
        existing.setContent(dto.getContent());
        existing.setRating(dto.getRating());

        if (dto.getUserId() != null) {
            AppUser user = appUserRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id " + dto.getUserId()));
            existing.setUser(user);
        }
        if (dto.getPlaceId() != null) {
            TouristPlace place = touristPlaceRepository.findById(dto.getPlaceId())
                    .orElseThrow(() -> new EntityNotFoundException("Place not found with id " + dto.getPlaceId()));
            existing.setPlace(place);
        }

        Review updated = repository.save(existing);
        return toDTO(updated);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Review not found with id " + id);
        }
        repository.deleteById(id);
    }

    private ReviewDTO toDTO(Review r) {
        ReviewDTO dto = modelMapper.map(r, ReviewDTO.class);
        dto.setUserId(r.getUser() != null ? r.getUser().getId() : null);
        dto.setPlaceId(r.getPlace() != null ? r.getPlace().getId() : null);
        return dto;
    }

    private Review toEntity(ReviewDTO dto) {
        Review r = modelMapper.map(dto, Review.class);

        if (dto.getUserId() != null) {
            AppUser user = appUserRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id " + dto.getUserId()));
            r.setUser(user);
        } else {
            r.setUser(null);
        }

        if (dto.getPlaceId() != null) {
            TouristPlace place = touristPlaceRepository.findById(dto.getPlaceId())
                    .orElseThrow(() -> new EntityNotFoundException("Place not found with id " + dto.getPlaceId()));
            r.setPlace(place);
        } else {
            r.setPlace(null);
        }

        return r;
    }
}
