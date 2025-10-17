package com.example.demo.repository;

import com.example.demo.model.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MunicipalityRepository extends JpaRepository<Municipality, Integer> {

    @Query(value = "SELECT * FROM Municipalities WHERE idState = :idState", nativeQuery = true)
    List<Municipality> findByState(@Param("idState") Integer idState);
}
