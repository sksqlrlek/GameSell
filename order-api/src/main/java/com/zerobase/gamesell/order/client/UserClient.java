package com.zerobase.gamesell.order.client;

import com.user.gamedomain.domain.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "userClient", url = "http://localhost:8081")
public interface UserClient {

  @GetMapping("/user/{userId}")
  UserDto getUser(@PathVariable("userId") Long userId, @RequestHeader("X-AUTH-TOKEN") String token);
}