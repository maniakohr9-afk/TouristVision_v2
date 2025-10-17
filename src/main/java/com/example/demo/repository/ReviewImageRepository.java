package com.example.demo.repository;


import com.example.demo.model.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Integer> {

    List<ReviewImage> findByReview_Id(Integer idReview);
}
