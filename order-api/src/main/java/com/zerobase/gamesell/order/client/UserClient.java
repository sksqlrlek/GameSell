package com.zerobase.gamesell.order.client;

import com.user.gamedomain.domain.Dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-api", url = "${url.user-api}")
public interface UserClient {
  @GetMapping("/user/{userId}")
  UserDto getUser(@PathVariable("userId") Long userId);
}