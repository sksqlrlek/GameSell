package com.zerobase.gamesell.order.domain.dto;

import com.zerobase.gamesell.order.domain.redis.Cart;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {

  private Cart cart;
  private List<String> messages;

}
