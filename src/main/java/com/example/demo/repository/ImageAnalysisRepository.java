package com.example.demo.repository;



import com.example.demo.model.ImageAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageAnalysisRepository extends JpaRepository<ImageAnalysis, Integer> {

    ImageAnalysis findByImage_Id(Integer idImage);
}
