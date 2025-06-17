package com.zerobase.gamesell.order.domain.game;

import com.zerobase.gamesell.order.domain.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGameForm {

  private Long id;
  private String title;
  private String description;
  private Integer price;
  private Genre genre;
  private Integer contentRating;
  private LocalDate releaseDate;
}
