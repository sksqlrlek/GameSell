package com.user.gamedomain.domain.Dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

  private Long id;
  private String email;
  private LocalDate birthDate;

}
