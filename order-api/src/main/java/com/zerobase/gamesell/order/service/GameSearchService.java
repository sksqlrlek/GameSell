package com.zerobase.gamesell.order.service;

import com.zerobase.gamesell.order.domain.dto.GameDocument;
import com.zerobase.gamesell.order.domain.repository.GameSearchRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameSearchService {

  private final GameSearchRepository gameSearchRepository;

  public List<GameDocument> searchByTitle(String keyword) {
    return gameSearchRepository.findByTitleContaining(keyword);
  }
}
