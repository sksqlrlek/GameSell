package com.zerobase.gamesell.order.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GameSearchResponse {

    private int currentPage;
    private int totalPages;
    private long totalCount;
    private List<GameDocument> games;
}
