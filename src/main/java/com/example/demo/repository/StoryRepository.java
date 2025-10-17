package com.example.demo.repository;

import com.example.demo.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Integer> {

    List<Story> findByPlace_Id(Integer idPlace);
}

