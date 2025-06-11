package com.zerobase.gamesell.order.domain.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddGameCartForm {
    private Long id;
    private Long sellerId;
    private String title;
    private Integer price;
    private Integer contentRating;

}
