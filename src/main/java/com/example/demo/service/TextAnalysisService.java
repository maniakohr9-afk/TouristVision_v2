package com.example.demo.service;

import com.example.demo.dto.TextAnalysisDTO;
import com.example.demo.model.Review;
import com.example.demo.model.TextAnalysis;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.TextAnalysisRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TextAnalysisService {

    @Autowired
    private TextAnalysisRepository repository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public TextAnalysisDTO getById(Integer id) {
        TextAnalysis ta = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TextAnalysis not found with id " + id));
        return toDTO(ta);
    }

    @Transactional(readOnly = true)
    public TextAnalysisDTO getByReview(Integer reviewId) {
        TextAnalysis ta = repository.findByReview_Id(reviewId);
        if (ta == null) {
            throw new EntityNotFoundException("TextAnalysis not found for review id " + reviewId);
        }
        return toDTO(ta);
    }

    public TextAnalysisDTO save(TextAnalysisDTO dto) {
        TextAnalysis entity = toEntity(dto);
        TextAnalysis saved = repository.save(entity);
        return toDTO(saved);
    }

    public TextAnalysisDTO update(TextAnalysisDTO dto, Integer id) {
        TextAnalysis existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TextAnalysis not found with id " + id));

        existing.setSentiment(dto.getSentiment());
        existing.setKeyPhrases(dto.getKeyPhrases());
        existing.setLanguage(dto.getLanguage());

        if (dto.getReviewId() != null) {
            Review review = reviewRepository.findById(dto.getReviewId())
                    .orElseThrow(() -> new EntityNotFoundException("Review not found with id " + dto.getReviewId()));
            existing.setReview(review);
        }

        TextAnalysis updated = repository.save(existing);
        return toDTO(updated);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("TextAnalysis not found with id " + id);
        }
        repository.deleteById(id);
    }


    private TextAnalysisDTO toDTO(TextAnalysis ta) {
        TextAnalysisDTO dto = modelMapper.map(ta, TextAnalysisDTO.class);
        dto.setReviewId(ta.getReview() != null ? ta.getReview().getId() : null);
        return dto;
    }

    private TextAnalysis toEntity(TextAnalysisDTO dto) {
        TextAnalysis ta = modelMapper.map(dto, TextAnalysis.class);
        if (dto.getReviewId() != null) {
            Review review = reviewRepository.findById(dto.getReviewId())
                    .orElseThrow(() -> new EntityNotFoundException("Review not found with id " + dto.getReviewId()));
            ta.setReview(review);
        }
        return ta;
    }
}
