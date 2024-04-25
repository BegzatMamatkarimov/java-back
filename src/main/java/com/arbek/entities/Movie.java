package com.arbek.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Movie {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer movieId;

  @Column(nullable = false)
  @NotBlank(message = "Movie's title couldn't be empty!")
  private String title;

  @Column(nullable = false)
  @NotBlank(message = "Movie's description couldn't be empty!")
  private String description;

  @Column(nullable = false)
  private Integer rating;

  @Column(nullable = false)
  private Integer releaseYear;

  @Column(nullable = false)
  private Integer duration;

  @ElementCollection
  @CollectionTable(name = "movieGenres")
  private List<String> genres;

  @ElementCollection
  @CollectionTable(name = "movieDirectors")
  private List<String> directors;

  @ElementCollection
  @CollectionTable(name = "movieStars")
  private List<String> stars;

  @Column(nullable = false)
  @NotBlank(message = "Movie's poster couldn't be empty!")
  private String posterSrc;

}
