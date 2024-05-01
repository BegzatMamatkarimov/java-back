package com.arbek.service;

import com.arbek.dto.MovieDto;
import com.arbek.dto.MoviePageResponse;
import com.arbek.entities.Movie;
import com.arbek.repositories.MovieRepositoty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

  private final MovieRepositoty movieRepository;

  private final FileService fileService;

  @Value("${project.poster}")
  private String path;

  @Value("${base.url}")
  private String baseUrl;

  public MovieServiceImpl(MovieRepositoty movieRepositoty, FileService fileService) {
    this.movieRepository = movieRepositoty;
    this.fileService = fileService;
  }

  @Override
  public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
    //1. upload file
    String uploadedFile = fileService.uploadFile(path, file);

    //. 2. set the value of filed "poster" as filename
    movieDto.setPoster(uploadedFile);

    // 3. map dto to Movie object
    Movie movie = new Movie(
        movieDto.getMovie_id(),
        movieDto.getTitle(),
        movieDto.getDescription(),
        movieDto.getRating(),
        movieDto.getReleaseYear(),
        movieDto.getDuration(),
        movieDto.getGenres(),
        movieDto.getDirectors(),
        movieDto.getStars(),
        movieDto.getPoster()
    );
    // 4. save the movie object -> saved Movie object
    Movie savedMovie = movieRepository.save(movie);

    // 5. generate the posterUrl
    String posterUrl = baseUrl + "/file/" + uploadedFile;

    // 6. map Movie object to DTO object and return it
    MovieDto response = new MovieDto(
        savedMovie.getMovie_id(),
        savedMovie.getTitle(),
        savedMovie.getDescription(),
        savedMovie.getRating(),
        savedMovie.getReleaseYear(),
        savedMovie.getDuration(),
        savedMovie.getGenres(),
        savedMovie.getDirectors(),
        savedMovie.getStars(),
        savedMovie.getPoster(),
        posterUrl
    );

    return response;
  }

  @Override
  public MovieDto getMovie(Integer movieId) {
    //1. провери бд если ест данные извлеч по id
    Movie movie = movieRepository.findById(Long.valueOf(movieId)).orElseThrow(() -> new RuntimeException("Movie not found"));

    //2. генерировать posterUrl
    String posterUrl = baseUrl + "/file/" + movie.getPoster();

    //3. соопоставит обектам movieDto и возврашят обратно
    MovieDto response = new MovieDto(
        movie.getMovie_id(),
        movie.getTitle(),
        movie.getDescription(),
        movie.getRating(),
        movie.getReleaseYear(),
        movie.getDuration(),
        movie.getGenres(),
        movie.getDirectors(),
        movie.getStars(),
        movie.getPoster(),
        posterUrl
    );
    return response;
  }

  @Override
  public List<MovieDto> getAlMovies() {
    //1. извлеч все данные из бд
    List<Movie> movies = movieRepository.findAll();

    List<MovieDto> movieDtos = new ArrayList<>();
    for (Movie movie : movies) {
      String posterUrl = baseUrl + "/file/" + movie.getPoster();
      MovieDto movieDto = new MovieDto(
          movie.getMovie_id(),
          movie.getTitle(),
          movie.getDescription(),
          movie.getRating(),
          movie.getReleaseYear(),
          movie.getDuration(),
          movie.getGenres(),
          movie.getDirectors(),
          movie.getStars(),
          movie.getPoster(),
          posterUrl
      );
      movieDtos.add(movieDto);
    }
    return movieDtos;
  }

  @Override
  public MovieDto updateMovie(Long movieId, MovieDto movieDto) throws IOException {
    // Проверяем, существует ли фильм с данным идентификатором
    Movie existingMovie = movieRepository.findById(movieId)
        .orElseThrow(() -> new RuntimeException("Movie not found"));

    // Обновляем данные фильма с использованием данных из DTO
    existingMovie.setTitle(movieDto.getTitle());
    existingMovie.setDescription(movieDto.getDescription());
    existingMovie.setRating(movieDto.getRating());
    existingMovie.setReleaseYear(movieDto.getReleaseYear());
    existingMovie.setDuration(movieDto.getDuration());
    existingMovie.setGenres(movieDto.getGenres());
    existingMovie.setDirectors(movieDto.getDirectors());
    existingMovie.setStars(movieDto.getStars());
    existingMovie.setPoster(movieDto.getPoster());

    // Сохраняем обновленный фильм в базе данных
    Movie updatedMovie = movieRepository.save(existingMovie);

    // Генерируем URL плаката
    String posterUrl = baseUrl + "/file/" + updatedMovie.getPoster();

    // Возвращаем обновленный фильм как DTO
    return new MovieDto(
        updatedMovie.getMovie_id(),
        updatedMovie.getTitle(),
        updatedMovie.getDescription(),
        updatedMovie.getRating(),
        updatedMovie.getReleaseYear(),
        updatedMovie.getDuration(),
        updatedMovie.getGenres(),
        updatedMovie.getDirectors(),
        updatedMovie.getStars(),
        updatedMovie.getPoster(),
        posterUrl
    );
  }

  @Override
  public void deleteMovie(Long movieId) {
    // Проверяем, существует ли фильм с данным идентификатором
    Movie movieToDelete = movieRepository.findById(movieId)
        .orElseThrow(() -> new RuntimeException("Movie not found"));

    // Удаляем фильм из базы данных
    movieRepository.delete(movieToDelete);
  }

  @Override
  public MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);

    Page<Movie> moviePages = movieRepository.findAll(pageable);
    List<Movie> movies = moviePages.getContent();

    List<MovieDto> movieDtos = new ArrayList<>();
    for (Movie movie : movies) {
      String posterUrl = baseUrl + "/file/" + movie.getPoster();
      MovieDto movieDto = new MovieDto(
          movie.getMovie_id(),
          movie.getTitle(),
          movie.getDescription(),
          movie.getRating(),
          movie.getReleaseYear(),
          movie.getDuration(),
          movie.getGenres(),
          movie.getDirectors(),
          movie.getStars(),
          movie.getPoster(),
          posterUrl
      );
      movieDtos.add(movieDto);
    }

    return new MoviePageResponse(movieDtos, pageNumber, pageSize,
        moviePages.getTotalPages(),
        moviePages.getTotalElements(),
        moviePages.isLast());
  }

  @Override
  public MoviePageResponse getAllMoviesWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String dir) {

    Sort sort = dir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

    Page<Movie> moviePages = movieRepository.findAll(pageable);
    List<Movie> movies = moviePages.getContent();

    List<MovieDto> movieDtos = new ArrayList<>();
    for (Movie movie : movies) {
      String posterUrl = baseUrl + "/file/" + movie.getPoster();
      MovieDto movieDto = new MovieDto(
          movie.getMovie_id(),
          movie.getTitle(),
          movie.getDescription(),
          movie.getRating(),
          movie.getReleaseYear(),
          movie.getDuration(),
          movie.getGenres(),
          movie.getDirectors(),
          movie.getStars(),
          movie.getPoster(),
          posterUrl
      );
      movieDtos.add(movieDto);
    }

    return new MoviePageResponse(movieDtos, pageNumber, pageSize,
        moviePages.getTotalPages(),
        moviePages.getTotalElements(),
        moviePages.isLast());
  }
}
