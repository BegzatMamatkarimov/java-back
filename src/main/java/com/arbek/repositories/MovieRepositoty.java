package com.arbek.repositories;

import com.arbek.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepositoty extends JpaRepository<Movie, Long> {
    // Поиск фильмов по названию
    List<Movie> findByTitleContainingIgnoreCase(String title);

    // Фильтр фильмов по жанру
    List<Movie> findByGenresContaining(String genre);
}
