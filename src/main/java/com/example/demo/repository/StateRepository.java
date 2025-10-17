package com.example.demo.repository;
import com.example.demo.model.State;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface StateRepository extends JpaRepository<State, Integer> {

    @Query(value = "SELECT * FROM States WHERE name ILIKE CONCAT('%', :name, '%')", nativeQuery = true)
    List<State> findByNameLike(@Param("name") String name);
}
