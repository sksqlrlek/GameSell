package com.zerobase.gamesell.order.domain.repository;

import com.zerobase.gamesell.order.domain.dto.GameDocument;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSearchRepository extends ElasticsearchRepository<GameDocument, Long> {

  List<GameDocument> findByTitleContaining(String keyword);
}
