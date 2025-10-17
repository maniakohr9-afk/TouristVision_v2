package com.example.demo.service;

import com.example.demo.dto.ReviewImageDTO;
import com.example.demo.model.Review;
import com.example.demo.model.ReviewImage;
import com.example.demo.repository.ReviewImageRepository;
import com.example.demo.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewImageService {

    @Autowired
    private ReviewImageRepository repository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<ReviewImageDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReviewImageDTO getById(Integer id) {
        ReviewImage img = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ReviewImage not found with id " + id));
        return toDTO(img);
    }

    @Transactional(readOnly = true)
    public List<ReviewImageDTO> getByReview(Integer reviewId) {
        return repository.findByReview_Id(reviewId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ReviewImageDTO save(ReviewImageDTO dto) {
        ReviewImage entity = toEntity(dto);
        ReviewImage saved = repository.save(entity);
        return toDTO(saved);
    }

    public ReviewImageDTO update(ReviewImageDTO dto, Integer id) {
        ReviewImage existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ReviewImage not found with id " + id));

        existing.setUrl(dto.getUrl());
        existing.setDescription(dto.getDescription());

        if (dto.getReviewId() != null) {
            Review review = reviewRepository.findById(dto.getReviewId())
                    .orElseThrow(() -> new EntityNotFoundException("Review not found with id " + dto.getReviewId()));
            existing.setReview(review);
        }

        ReviewImage updated = repository.save(existing);
        return toDTO(updated);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("ReviewImage not found with id " + id);
        }
        repository.deleteById(id);
    }

    private ReviewImageDTO toDTO(ReviewImage img) {
        ReviewImageDTO dto = modelMapper.map(img, ReviewImageDTO.class);
        dto.setReviewId(img.getReview() != null ? img.getReview().getId() : null);
        return dto;
    }

    private ReviewImage toEntity(ReviewImageDTO dto) {
        ReviewImage img = modelMapper.map(dto, ReviewImage.class);
        if (dto.getReviewId() != null) {
            Review review = reviewRepository.findById(dto.getReviewId())
                    .orElseThrow(() -> new EntityNotFoundException("Review not found with id " + dto.getReviewId()));
            img.setReview(review);
        }
        return img;
    }
}
