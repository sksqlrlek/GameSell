package com.zerobase.gamesell.order.domain.model;

import com.zerobase.gamesell.order.domain.game.AddGameForm;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited
@AuditOverride(forClass = BaseEntity.class)
public class Game extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "seller_id", nullable = false)
  private Long sellerId;

  @Column(nullable = false, length = 50)
  private String title;

  @Column(length = 1000)
  private String description;

  @Column(nullable = false)
  private Integer price;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  private Genre genre;

  @Column(nullable = false)
  private Integer contentRating;

  @Column(name = "release_date", nullable = false)
  private LocalDate releaseDate;

  public static Game of(Long sellerId, AddGameForm form) {
    return Game.builder().sellerId(sellerId)
        .title(form.getTitle())
        .description(form.getDescription())
        .price(form.getPrice())
        .genre(form.getGenre())
        .contentRating(form.getContentRating())
        .releaseDate(form.getReleaseDate())
        .build();
  }

}
