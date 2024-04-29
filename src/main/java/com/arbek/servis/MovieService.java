package com.arbek.servis;

import com.arbek.dto.MovieDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface MovieService {

    MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException;

    MovieDto getMovie(Integer movieId);

    List<MovieDto> getAlMovies();

    MovieDto updateMovie(Long movieId, MovieDto movieDto) throws IOException;

    void deleteMovie(Long movieId);
}
