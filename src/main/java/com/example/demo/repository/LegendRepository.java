package com.example.demo.repository;

import com.example.demo.model.Legend;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LegendRepository extends JpaRepository<Legend, Integer> {

    List<Legend> findByPlace_Id(Integer idPlace);
}
