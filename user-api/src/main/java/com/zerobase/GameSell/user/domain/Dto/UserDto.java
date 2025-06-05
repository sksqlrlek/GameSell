package com.zerobase.GameSell.user.domain.Dto;

import com.zerobase.GameSell.user.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserDto {

  private Long id;
  private String email;


  public static UserDto from(User user) {
    return new UserDto(
        user.getId(),
        user.getEmail());
  }
}
