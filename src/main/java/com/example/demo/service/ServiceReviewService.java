package com.example.demo.service;

import com.example.demo.dto.ServiceReviewDTO;
import com.example.demo.model.AppUser;
import com.example.demo.model.ServiceReview;
import com.example.demo.model.Service;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.repository.ServiceRepository;
import com.example.demo.repository.ServiceReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Transactional
public class ServiceReviewService {

    @Autowired
    private ServiceReviewRepository repository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<ServiceReviewDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ServiceReviewDTO getById(Integer id) {
        ServiceReview review = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ServiceReview not found with id " + id));
        return toDTO(review);
    }

    @Transactional(readOnly = true)
    public List<ServiceReviewDTO> getByService(Integer serviceId) {
        return repository.findByServiceId(serviceId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ServiceReviewDTO save(ServiceReviewDTO dto) {
        ServiceReview entity = toEntity(dto);
        ServiceReview saved = repository.save(entity);
        return toDTO(saved);
    }

    public ServiceReviewDTO update(ServiceReviewDTO dto, Integer id) {
        ServiceReview existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ServiceReview not found with id " + id));

        existing.setRating(dto.getRating());
        existing.setComment(dto.getComment());

        if (dto.getServiceId() != null) {
            Service service = serviceRepository.findById(dto.getServiceId())
                    .orElseThrow(() -> new EntityNotFoundException("Service not found with id " + dto.getServiceId()));
            existing.setService(service);
        }

        if (dto.getUserId() != null) {
            AppUser user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id " + dto.getUserId()));
            existing.setUser(user);
        }

        ServiceReview updated = repository.save(existing);
        return toDTO(updated);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("ServiceReview not found with id " + id);
        }
        repository.deleteById(id);
    }

    private ServiceReviewDTO toDTO(ServiceReview review) {
        ServiceReviewDTO dto = modelMapper.map(review, ServiceReviewDTO.class);
        dto.setServiceId(review.getService() != null ? review.getService().getId() : null);
        dto.setUserId(review.getUser() != null ? review.getUser().getId() : null);
        return dto;
    }

    private ServiceReview toEntity(ServiceReviewDTO dto) {
        ServiceReview entity = modelMapper.map(dto, ServiceReview.class);

        if (dto.getServiceId() != null) {
            Service service = serviceRepository.findById(dto.getServiceId())
                    .orElseThrow(() -> new EntityNotFoundException("Service not found with id " + dto.getServiceId()));
            entity.setService(service);
        }

        if (dto.getUserId() != null) {
            AppUser user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id " + dto.getUserId()));
            entity.setUser(user);
        }

        return entity;
    }
}
