package com.zerobase.GameSell.user.domain.repository;

import com.user.gamedomain.domain.dto.UserDto;
import com.zerobase.GameSell.user.domain.model.User;

public class UserDtoMapper {

  public static UserDto from(User user) {
    return new UserDto(user.getId(), user.getEmail(), user.getBirthDate());
  }
}
