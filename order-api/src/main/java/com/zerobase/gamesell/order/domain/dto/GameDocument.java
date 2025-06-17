package com.zerobase.gamesell.order.domain.dto;

import com.zerobase.gamesell.order.domain.model.Game;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(indexName = "games")
public class GameDocument {

  @Id
  private Long id;

  private String title;
  private String description;


  public static GameDocument from(Game game) {
    GameDocument doc = new GameDocument();
    doc.setId(game.getId());
    doc.setTitle(game.getTitle());
    doc.setDescription(game.getDescription());
    return doc;
  }
}
