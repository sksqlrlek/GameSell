package com.zerobase.gamesell.order.service;

import com.zerobase.gamesell.order.domain.dto.GameDocument;
import com.zerobase.gamesell.order.domain.dto.GameSearchResponse;
import com.zerobase.gamesell.order.domain.repository.GameSearchRepository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameSearchService {

    private final GameSearchRepository gameSearchRepository;

    public GameSearchResponse searchGame(String keyword, String genre, int page) {

        Pageable pageable = PageRequest.of(page, 25);
        Page<GameDocument> result;

        // 제목 + 장르 검색
        if (keyword != null && !keyword.isBlank() && genre != null && !genre.isBlank()) {
            result = gameSearchRepository.findByTitleContainingAndGenre(keyword, genre.toUpperCase(), pageable);
        } // 장르만 검색
        else if (genre != null && !genre.isBlank()) {
            result = gameSearchRepository.findByGenre(genre.toUpperCase(), pageable);
        } // 제목만 검색
        else if (keyword != null && !keyword.isBlank()) {
            result = gameSearchRepository.findByTitleContaining(keyword, pageable);
        } // 아무 파라미터도 없으면 전체 검색
        else {
            result = gameSearchRepository.findAll(pageable);
        }

        return new GameSearchResponse(
                result.getNumber(),
                result.getTotalPages(),
                result.getTotalElements(),
                result.getContent()
        );
    }
}
