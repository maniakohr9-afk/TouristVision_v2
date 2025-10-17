package com.example.demo.repository;


import com.example.demo.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Integer> {

    List<Service> findByPlace_Id(Integer idPlace);
}
