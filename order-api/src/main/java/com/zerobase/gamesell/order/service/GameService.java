package com.zerobase.gamesell.order.service;

import com.zerobase.gamesell.order.domain.game.AddGameForm;
import com.zerobase.gamesell.order.domain.game.UpdateGameForm;
import com.zerobase.gamesell.order.domain.model.Game;
import com.zerobase.gamesell.order.domain.repository.GameRepository;
import com.zerobase.gamesell.order.exception.CustomException;
import com.zerobase.gamesell.order.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GameService {
  private final GameRepository gameRepository;

  @Transactional
  public Game addGame(Long sellerId, AddGameForm form) {
    return gameRepository.save(Game.of(sellerId, form));
  }

  @Transactional
  public Game updateGame(Long sellerId, UpdateGameForm form) {
    Game game = gameRepository.findBySellerIdAndId(sellerId, form.getId())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_GAME));
    game.setTitle(form.getTitle());
    game.setDescription(form.getDescription());
    game.setPrice(form.getPrice());
    game.setGenre(form.getGenre());
    game.setContentRating(form.getContentRating());
    game.setReleaseDate(form.getReleaseDate());

    return game;
  }

  @Transactional
  public void deleteGame(Long sellerId, Long gameId) {
    Game game = gameRepository.findBySellerIdAndId(sellerId, gameId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_GAME));
    gameRepository.delete(game);
  }
}
