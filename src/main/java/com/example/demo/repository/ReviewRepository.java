package com.example.demo.repository;


import com.example.demo.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query(value = "SELECT * FROM Reviews WHERE idPlace = :idPlace", nativeQuery = true)
    List<Review> findByPlace(@Param("idPlace") Integer idPlace);
}
