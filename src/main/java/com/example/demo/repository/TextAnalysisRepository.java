package com.example.demo.repository;



import com.example.demo.model.TextAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextAnalysisRepository extends JpaRepository<TextAnalysis, Integer> {

    TextAnalysis findByReview_Id(Integer idReview);
}
