package com.zerobase.gamesell.order.controller;

import com.user.gamedomain.config.JwtAuthenticationProvider;
import com.zerobase.gamesell.order.domain.dto.GameDto;
import com.zerobase.gamesell.order.domain.game.AddGameForm;
import com.zerobase.gamesell.order.domain.game.UpdateGameForm;
import com.zerobase.gamesell.order.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller/game")
@RequiredArgsConstructor
public class SellerGameController {

  private final GameService gameService;
  private final JwtAuthenticationProvider provider;

  @PostMapping
  public ResponseEntity<GameDto> addGame(@RequestHeader(name = "X-AUTH-TOKEN") String token,
      @RequestBody AddGameForm form) {

    return ResponseEntity.ok(
        GameDto.from(gameService.addGame(provider.getUserVo(token).getId(), form)));
  }

  @PutMapping
  public ResponseEntity<GameDto> updateGame(@RequestHeader(name = "X-AUTH-TOKEN") String token,
      @RequestBody UpdateGameForm form) {

    return ResponseEntity.ok(
        GameDto.from(gameService.updateGame(provider.getUserVo(token).getId(), form)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteGame(@RequestHeader(name = "X-AUTH-TOKEN") String token,
      @PathVariable("id") Long id) {
    gameService.deleteGame(provider.getUserVo(token).getId(), id);
    return ResponseEntity.ok().build();
  }

}
