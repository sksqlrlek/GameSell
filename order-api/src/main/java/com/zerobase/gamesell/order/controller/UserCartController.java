package com.zerobase.gamesell.order.controller;

import com.user.gamedomain.config.JwtAuthenticationProvider;
import com.zerobase.gamesell.order.application.CartApplication;
import com.zerobase.gamesell.order.domain.dto.CartResponse;
import com.zerobase.gamesell.order.domain.dto.RemoveCartResponse;
import com.zerobase.gamesell.order.domain.game.AddGameCartForm;
import com.zerobase.gamesell.order.domain.redis.Cart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.annotation.PostConstruct;


@Slf4j
@RestController
@RequiredArgsConstructor
public class UserCartController {

  private final CartApplication cartApplication;
  private final JwtAuthenticationProvider provider;

  @PostConstruct
  public void init() {
    log.info("UserCartController 빈 등록됨");
  }

  @PostMapping("/user/cart")
  public ResponseEntity<Cart> addCart(@RequestHeader(name = "X-AUTH-TOKEN") String token,
      @RequestBody AddGameCartForm form) {
    log.info("✅ addCart 메서드 진입 - gameId: {}", form.getId());

    return ResponseEntity.ok(cartApplication.addCart(provider.getUserVo(token).getId(), form, token));
  }

  @GetMapping("/user/cart")
  public ResponseEntity<CartResponse> showCart(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
    log.info("==== showCart 호출됨 ====");
    log.info("token: {}", token);
    Long userId = provider.getUserVo(token).getId();
    log.info("userId: {}", userId);

    CartResponse cartResponse = cartApplication.getCart(userId, token);
    log.info("cartResponse: {}", cartResponse);

    return ResponseEntity.ok(cartResponse);
  }

  @PutMapping("/user/cart/remove")
  public ResponseEntity<Cart> removeCart(
      @RequestHeader(name = "X-AUTH-TOKEN") String token,
      @RequestBody RemoveCartResponse request) {

    Long userId = provider.getUserVo(token).getId();
    Cart updatedCart = cartApplication.removeGameCart(userId, request.getGameId());
    return ResponseEntity.ok(updatedCart);
  }

  @PutMapping("/user/cart/clear")
  public ResponseEntity<Void> clearCart(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
    cartApplication.clearCart(provider.getUserVo(token).getId());
    return ResponseEntity.ok().build();
  }

}
