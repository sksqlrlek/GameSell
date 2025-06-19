package com.zerobase.gamesell.order.service;

import com.zerobase.gamesell.order.domain.dto.GameDocument;
import com.zerobase.gamesell.order.domain.model.Game;
import com.zerobase.gamesell.order.domain.repository.GameSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameIndexingService {

    private final GameSearchRepository gameSearchRepository;

    public void indexGame(Game game) {
        gameSearchRepository.save(GameDocument.from(game));
    }

}
