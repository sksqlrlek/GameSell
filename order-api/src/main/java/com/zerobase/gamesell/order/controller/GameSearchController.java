package com.zerobase.gamesell.order.controller;

import com.zerobase.gamesell.order.domain.dto.GameDocument;
import com.zerobase.gamesell.order.domain.dto.GameSearchResponse;
import com.zerobase.gamesell.order.service.GameSearchService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GameSearchController {

    private final GameSearchService gameSearchService;

    @GetMapping("/games/search")
    public GameSearchResponse searchGames(@RequestParam(required = false) String keyword,
                                          @RequestParam(required = false) String genre,
                                          @RequestParam(defaultValue = "0") int page) {
        return gameSearchService.searchGame(keyword, genre, page);
    }
}
