package com.zerobase.gamesell.order.controller;

import com.user.gamedomain.config.JwtAuthenticationProvider;
import com.zerobase.gamesell.order.application.CartApplication;
import com.zerobase.gamesell.order.domain.dto.CartResponse;
import com.zerobase.gamesell.order.domain.game.AddGameCartForm;
import com.zerobase.gamesell.order.domain.redis.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/cart")
@RequiredArgsConstructor
public class UserCartController {

  private final CartApplication cartApplication;
  private final JwtAuthenticationProvider provider;

  @PostMapping
  public ResponseEntity<Cart> addCart(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                      @RequestBody AddGameCartForm form) {
    return ResponseEntity.ok(cartApplication.addCart(provider.getUserVo(token).getId(), form));
  }

  @PutMapping
  public ResponseEntity<CartResponse> showCart(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
    Long userId = provider.getUserVo(token).getId();
    CartResponse cartResponse = cartApplication.getCart(userId);
    return ResponseEntity.ok(cartResponse);
  }

  @PutMapping
  public ResponseEntity<Cart> removeCart(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                        @RequestParam Long gameId) {
    return ResponseEntity.ok(cartApplication.removeGameCart(provider.getUserVo(token).getId(), gameId));
  }

  @PutMapping("/clear")
  public ResponseEntity<Void> clearCart(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
    cartApplication.clearCart(provider.getUserVo(token).getId());
    return ResponseEntity.ok().build();
  }

}
