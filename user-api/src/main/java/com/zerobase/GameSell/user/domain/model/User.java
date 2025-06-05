package com.zerobase.GameSell.user.domain.model;

import com.user.gamedomain.domain.common.UserType;
import com.zerobase.GameSell.user.domain.SignUpForm;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class User extends BaseEntity {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true, nullable = false, length = 100)
  private String email;
  @Column(nullable = false, length = 50)
  private String password;
  @Column(nullable = false)
  private LocalDate birthDate;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 1)
  private Gender gender;
  @Enumerated(EnumType.STRING)
  private UserType role;

  private LocalDateTime verifyExpiredAt;
  private String verificationCode;
  private LocalDateTime verifiedAt;

  public static User from(SignUpForm form) {
    return User.builder()
        .email(form.getEmail().toLowerCase(Locale.ROOT))
        .password(form.getPassword())
        .birthDate(form.getBirthDate())
        .gender(form.getGender())
        .role(form.getRole())
        .verifiedAt(null)
        .build();
  }

}
