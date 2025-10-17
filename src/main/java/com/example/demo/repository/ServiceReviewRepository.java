package com.example.demo.repository;


import com.example.demo.model.ServiceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServiceReviewRepository extends JpaRepository<ServiceReview, Integer> {

    List<ServiceReview> findByServiceId(Integer idService);
}
