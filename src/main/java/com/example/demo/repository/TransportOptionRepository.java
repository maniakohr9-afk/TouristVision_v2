package com.example.demo.repository;

import com.example.demo.model.TransportOption;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransportOptionRepository extends JpaRepository<TransportOption, Integer> {

    List<TransportOption> findByPlace_Id(Integer idPlace);
}
