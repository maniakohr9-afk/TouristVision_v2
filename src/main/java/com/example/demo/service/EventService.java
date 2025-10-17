package com.example.demo.service;

import com.example.demo.dto.EventDTO;
import com.example.demo.model.Event;
import com.example.demo.model.TouristPlace;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.TouristPlaceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {

    @Autowired
    private EventRepository repository;

    @Autowired
    private TouristPlaceRepository touristPlaceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<EventDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EventDTO getById(Integer id) {
        Event ev = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id " + id));
        return toDTO(ev);
    }

    @Transactional(readOnly = true)
    public List<EventDTO> getByPlace(Integer placeId) {
        return repository.findByPlaceId(placeId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EventDTO> getUpcoming() {
        MonthDay today = MonthDay.from(LocalDate.now());
        return repository.findAll().stream()
                .filter(e -> {
                    if (e.getEventDate() == null)
                        return false;
                    try {
                        String[] parts = e.getEventDate().split("-");
                        MonthDay md = MonthDay.of(Integer.parseInt(parts[1]), Integer.parseInt(parts[0]));
                        return !md.isBefore(today);
                    } catch (Exception ex) {
                        return false;
                    }
                })
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EventDTO> getByDateRange(String start, String end) {
        return repository.findAll().stream()
                .filter(e -> {
                    if (e.getEventDate() == null)
                        return false;
                    String date = e.getEventDate();
                    return date.compareTo(start) >= 0 && date.compareTo(end) <= 0;
                })
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public EventDTO save(EventDTO dto) {
        Event entity = toEntity(dto);
        Event saved = repository.save(entity);
        return toDTO(saved);
    }

    public EventDTO update(EventDTO dto, Integer id) {
        Event existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id " + id));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setEventDate(dto.getEventDate());

        if (dto.getPlaceId() != null) {
            TouristPlace place = touristPlaceRepository.findById(dto.getPlaceId())
                    .orElseThrow(
                            () -> new EntityNotFoundException("TouristPlace not found with id " + dto.getPlaceId()));
            existing.setPlace(place);
        }

        Event updated = repository.save(existing);
        return toDTO(updated);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Event not found with id " + id);
        }
        repository.deleteById(id);
    }

    private EventDTO toDTO(Event e) {
        EventDTO dto = modelMapper.map(e, EventDTO.class);
        dto.setPlaceId(e.getPlace() != null ? e.getPlace().getId() : null);
        return dto;
    }

    private Event toEntity(EventDTO dto) {
        Event e = modelMapper.map(dto, Event.class);
        if (dto.getPlaceId() != null) {
            TouristPlace place = touristPlaceRepository.findById(dto.getPlaceId())
                    .orElseThrow(
                            () -> new EntityNotFoundException("TouristPlace not found with id " + dto.getPlaceId()));
            e.setPlace(place);
        } else {
            e.setPlace(null);
        }
        return e;
    }
}
