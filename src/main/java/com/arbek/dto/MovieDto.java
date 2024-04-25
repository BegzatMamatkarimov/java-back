package com.arbek.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {
  private Integer movieId;

  @NotBlank(message = "Movie's title couldn't be empty!")
  private String title;

  @NotBlank(message = "Movie's description couldn't be empty!")
  private String description;

  private Integer rating;

  private Integer releaseYear;

  private Integer duration;

  private List<String> genres;

  private List<String> directors;

  private List<String> stars;

  @NotBlank(message = "Movie's poster couldn't be empty!")
  private String posterSrc;
}
