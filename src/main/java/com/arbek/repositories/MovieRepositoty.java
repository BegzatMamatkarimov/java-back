package com.arbek.repositories;

import com.arbek.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepositoty extends JpaRepository<Movie, Long> {
}
