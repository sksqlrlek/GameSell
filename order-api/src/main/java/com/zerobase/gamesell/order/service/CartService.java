package com.zerobase.gamesell.order.service;

import com.zerobase.gamesell.order.client.RedisClient;
import com.zerobase.gamesell.order.domain.game.AddGameCartForm;
import com.zerobase.gamesell.order.domain.redis.Cart;
import com.zerobase.gamesell.order.exception.CustomException;
import com.zerobase.gamesell.order.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.zerobase.gamesell.order.exception.ErrorCode.ALREADY_IN_CART;
import static com.zerobase.gamesell.order.exception.ErrorCode.GAME_PRICE_CHANGED;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {

  private final RedisClient redisClient;

  public Cart getCart(Long userId) {
    Cart cart = redisClient.get(userId, Cart.class);
    if (cart == null) {
      cart = new Cart();
      cart.setUserId(userId);
    }
    return cart;
  }

  public Cart putCart(Long userId, Cart cart) {
    cart.setUserId(userId);
    redisClient.put(userId, cart);
    return cart;
  }

  public Cart addCart(Long userId, AddGameCartForm form) {

    Cart cart = redisClient.get(userId, Cart.class);
    if (cart == null) {
      cart = new Cart();
      cart.setUserId(userId);
    }

    // 같은 게임이 이미 있는 경우
    if (cart.getGames().stream()
        .anyMatch(game -> game.getId().equals(form.getId()))) {
      throw new CustomException(ALREADY_IN_CART);
    }
    Cart.Game game = Cart.Game.from(form);
    cart.getGames().add(game);
    redisClient.put(userId, cart);
    return cart;
  }

  public void clearCart(Long userId) {
    redisClient.delete(userId);
  }
}
