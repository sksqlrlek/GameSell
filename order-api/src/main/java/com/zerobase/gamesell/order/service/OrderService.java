package com.zerobase.gamesell.order.service;

import static com.zerobase.gamesell.order.exception.ErrorCode.CART_IS_EMPTY;
import static com.zerobase.gamesell.order.exception.ErrorCode.NOT_FOUND_ORDER;

import com.zerobase.gamesell.order.client.RedisClient;
import com.zerobase.gamesell.order.domain.dto.OrderItemResponse;
import com.zerobase.gamesell.order.domain.dto.OrderResponse;
import com.zerobase.gamesell.order.domain.model.Order;
import com.zerobase.gamesell.order.domain.model.OrderItem;
import com.zerobase.gamesell.order.domain.model.OrderStatus;
import com.zerobase.gamesell.order.domain.redis.Cart;
import com.zerobase.gamesell.order.domain.redis.Cart.Game;
import com.zerobase.gamesell.order.domain.repository.GameRepository;
import com.zerobase.gamesell.order.domain.repository.OrderItemRepository;
import com.zerobase.gamesell.order.domain.repository.OrderRepository;
import com.zerobase.gamesell.order.exception.CustomException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final CartService cartService;
  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;
  private final GameRepository gameRepository;
  private final RedisClient redisClient;

  public Order createOrderFromCart(Long userId) {

    Cart cart = redisClient.get(userId, Cart.class);
    if (cart == null || cart.getGames() == null || cart.getGames().isEmpty()) {
      throw new CustomException(CART_IS_EMPTY);
    }
    List<Game> games = cart.getGames();

    if (games.isEmpty()) {
      throw new CustomException(CART_IS_EMPTY);
    }

    int totalPrice = games.stream().mapToInt(Cart.Game::getPrice).sum();

    Order order = Order.builder().userId(userId).price(totalPrice).orderStatus(OrderStatus.PENDING)
        .orderedDate(LocalDateTime.now()).build();
    order = orderRepository.save(order);

    for (Cart.Game game : games) {
      OrderItem item = OrderItem.builder().order(order).gameId(game.getId()).price(game.getPrice())
          .build();

      orderItemRepository.save(item);
    }

    cartService.clearCart(userId);
    return order;
  }

  public List<OrderResponse> getOrderByUser(Long userId) {
    List<Order> orders = orderRepository.findByUserId(userId);

    return orders.stream().map(order -> {
      List<OrderItemResponse> itemResponses = orderItemRepository.findAllByOrder(order).stream()
          .map(item -> new OrderItemResponse(
              item.getId(),
              gameRepository.findById(item.getGameId())
                  .map(game -> game.getTitle())
                  .orElse("Unknown Game"),
              item.getPrice()))
          .collect(Collectors.toList());

      return OrderResponse.builder()
          .orderId(order.getId())
          .items(itemResponses)
          .price(order.getPrice())
          .status(order.getOrderStatus())
          .orderedDate(order.getOrderedDate())
          .build();
    }).collect(Collectors.toList());

  }

  public Order getOrderById(Long orderId) {
    return orderRepository.findById(orderId)
        .orElseThrow(() -> new CustomException(NOT_FOUND_ORDER));
  }
}
