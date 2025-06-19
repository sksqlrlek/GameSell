package com.zerobase.gamesell.order.domain.repository;

import com.zerobase.gamesell.order.domain.dto.GameDocument;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSearchRepository extends ElasticsearchRepository<GameDocument, Long> {

    Page<GameDocument> findByTitleContaining(String keyword, Pageable pageable);

    Page<GameDocument> findByGenre(String genre, Pageable pageable);

    Page<GameDocument> findByTitleContainingAndGenre(String keyword, String genre, Pageable pageable);
}
