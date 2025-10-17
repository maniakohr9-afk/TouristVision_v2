package com.example.demo.repository;


import com.example.demo.model.FavoritePlace;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FavoritePlaceRepository extends JpaRepository<FavoritePlace, Integer> {

    List<FavoritePlace> findByUserId(Integer idUser);
}
