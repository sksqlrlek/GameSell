package com.zerobase.gamesell.order.domain.repository;

import com.zerobase.gamesell.order.domain.model.Game;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

  Optional<Game> findBySellerIdAndId(Long sellerId, Long id);

}
