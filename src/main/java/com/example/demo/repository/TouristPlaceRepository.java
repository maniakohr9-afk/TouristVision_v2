package com.example.demo.repository;

import com.example.demo.model.TouristPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TouristPlaceRepository extends JpaRepository<TouristPlace, Integer> {

    @Query(value = "SELECT * FROM TouristPlaces WHERE idMunicipality = :idMunicipality", nativeQuery = true)
    List<TouristPlace> findByMunicipality(@Param("idMunicipality") Integer idMunicipality);
}
