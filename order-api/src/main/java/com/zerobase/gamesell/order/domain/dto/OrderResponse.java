package com.zerobase.gamesell.order.domain.dto;

import com.zerobase.gamesell.order.domain.model.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

  private Long orderId;
  private List<OrderItemResponse> items;
  private Integer price;
  private OrderStatus status;
  private LocalDateTime orderedDate;
}
