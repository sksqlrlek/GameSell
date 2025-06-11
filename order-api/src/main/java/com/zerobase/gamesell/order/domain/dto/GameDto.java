package com.zerobase.gamesell.order.domain.dto;

import com.zerobase.gamesell.order.domain.model.Game;
import com.zerobase.gamesell.order.domain.model.Genre;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GameDto {
  private Long id;
  private String title;
  private String description;
  private Integer price;
  private Genre genre;
  private Integer contentRating;
  private LocalDate releaseDate;

  public static GameDto from(Game game) {
    return GameDto.builder()
        .id(game.getId())
        .title(game.getTitle())
        .description(game.getDescription())
        .price(game.getPrice())
        .genre(game.getGenre())
        .contentRating(game.getContentRating())
        .releaseDate(game.getReleaseDate())
        .build();
  }
}
