package com.zerobase.gamesell.order.domain.redis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.zerobase.gamesell.order.domain.game.AddGameCartForm;
import com.zerobase.gamesell.order.domain.redis.Cart.Game;
import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RedisHash("cart")
public class Cart {

  @Id
  private Long userId;
  @JsonDeserialize(contentAs = Cart.Game.class)
  private List<Game> games = new ArrayList<>();
  private LocalDateTime createdAt = LocalDateTime.now();

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Game {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;
    @JsonProperty("price")
    private Integer price;
    @JsonProperty("contentRating")
    private Integer contentRating;

    public static Game from(AddGameCartForm form) {
      return Game.builder()
          .id(form.getId())
          .title(form.getTitle())
          .price(form.getPrice())
          .contentRating(form.getContentRating())
          .build();
    }
  }
}
