package com.zerobase.GameSell.user.domain;

import com.user.gamedomain.domain.common.UserType;
import com.zerobase.GameSell.user.domain.model.Gender;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpForm {

  private String email;
  private String password;
  private LocalDate birthDate;
  private Gender gender;
  private UserType role;

  // seller 전용 필드
  private String company;
  private String bank;
  private String number;
}
