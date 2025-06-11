package com.zerobase.gamesell.order.domain.game;

import com.zerobase.gamesell.order.domain.model.Genre;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddGameForm {
  private String title;
  private String description;
  private Integer price;
  private Genre genre;
  private Integer contentRating;
  private LocalDate releaseDate;
}
