package com.zerobase.gamesell.order.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemResponse {

  private Long orderItemId;
  private String gameTitle;
  private Integer price;
}
