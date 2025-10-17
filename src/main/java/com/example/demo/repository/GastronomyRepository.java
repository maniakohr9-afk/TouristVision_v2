package com.example.demo.repository;

import com.example.demo.model.Gastronomy;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GastronomyRepository extends JpaRepository<Gastronomy, Integer> {

    List<Gastronomy> findByPlaceId(Integer idPlace);
}
