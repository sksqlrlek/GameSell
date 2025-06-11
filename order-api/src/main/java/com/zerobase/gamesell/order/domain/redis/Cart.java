package com.zerobase.gamesell.order.domain.redis;

import com.zerobase.gamesell.order.domain.game.AddGameCartForm;
import com.zerobase.gamesell.order.domain.model.Game;
import jakarta.persistence.Id;
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

    private List<Game> games = new ArrayList<>();
    private LocalDateTime createdAt = LocalDateTime.now();

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Game {
        private Long id;
        private Long sellerId;
        private String title;
        private Integer price;
        private Integer contentRating;

        public static Game from(AddGameCartForm form) {
            return Game.builder()
                    .id(form.getId())
                    .sellerId(form.getSellerId())
                    .title(form.getTitle())
                    .price(form.getPrice())
                    .contentRating(form.getContentRating())
                    .build();
        }
    }
}
