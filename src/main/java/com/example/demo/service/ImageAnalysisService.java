package com.example.demo.service;

import com.example.demo.dto.ImageAnalysisDTO;
import com.example.demo.model.ImageAnalysis;
import com.example.demo.model.ReviewImage;
import com.example.demo.repository.ImageAnalysisRepository;
import com.example.demo.repository.ReviewImageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ImageAnalysisService {

    @Autowired
    private ImageAnalysisRepository repository;

    @Autowired
    private ReviewImageRepository reviewImageRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public ImageAnalysisDTO getById(Integer id) {
        ImageAnalysis analysis = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ImageAnalysis not found with id " + id));
        return toDTO(analysis);
    }

    @Transactional(readOnly = true)
    public ImageAnalysisDTO getByImage(Integer imageId) {
        ImageAnalysis analysis = repository.findByImage_Id(imageId);
        if (analysis == null) {
            throw new EntityNotFoundException("ImageAnalysis not found for image id " + imageId);
        }
        return toDTO(analysis);
    }

    public ImageAnalysisDTO save(ImageAnalysisDTO dto) {
        ImageAnalysis entity = toEntity(dto);
        ImageAnalysis saved = repository.save(entity);
        return toDTO(saved);
    }

    public ImageAnalysisDTO update(ImageAnalysisDTO dto, Integer id) {
        ImageAnalysis existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ImageAnalysis not found with id " + id));

        existing.setConfidence(dto.getConfidence());
        existing.setDescription(dto.getDescription());

        if (dto.getImageId() != null) {
            ReviewImage img = reviewImageRepository.findById(dto.getImageId())
                    .orElseThrow(() -> new EntityNotFoundException("ReviewImage not found with id " + dto.getImageId()));
            existing.setImage(img);
        }

        ImageAnalysis updated = repository.save(existing);
        return toDTO(updated);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("ImageAnalysis not found with id " + id);
        }
        repository.deleteById(id);
    }

    private ImageAnalysisDTO toDTO(ImageAnalysis analysis) {
        ImageAnalysisDTO dto = modelMapper.map(analysis, ImageAnalysisDTO.class);
        dto.setImageId(analysis.getImage() != null ? analysis.getImage().getId() : null);
        return dto;
    }

    private ImageAnalysis toEntity(ImageAnalysisDTO dto) {
        ImageAnalysis entity = modelMapper.map(dto, ImageAnalysis.class);
        if (dto.getImageId() != null) {
            ReviewImage img = reviewImageRepository.findById(dto.getImageId())
                    .orElseThrow(() -> new EntityNotFoundException("ReviewImage not found with id " + dto.getImageId()));
            entity.setImage(img);
        }
        return entity;
    }
}
