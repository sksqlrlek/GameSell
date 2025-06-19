package com.zerobase.gamesell.order.domain.dto;

import com.zerobase.gamesell.order.domain.model.Game;
import com.zerobase.gamesell.order.domain.model.Genre;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@NoArgsConstructor
@Document(indexName = "games")
public class GameDocument {

    @Id
    private Long id;

    private String title;
    private String description;

    @Field(type = FieldType.Keyword)  // 중요!
    private Genre genre;

    public static GameDocument from(Game game) {
        GameDocument doc = new GameDocument();
        doc.setId(game.getId());
        doc.setTitle(game.getTitle());
        doc.setDescription(game.getDescription());
        doc.setGenre(game.getGenre());
        return doc;
    }
}
