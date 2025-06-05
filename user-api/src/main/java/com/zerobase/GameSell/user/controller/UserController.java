package com.zerobase.GameSell.user.controller;

import static com.zerobase.GameSell.user.exception.ErrorCode.NOT_FOUND_USER;

import com.user.gamedomain.config.JwtAuthenticationProvider;
import com.user.gamedomain.domain.common.UserVo;
import com.zerobase.GameSell.user.domain.Dto.UserDto;
import com.zerobase.GameSell.user.domain.model.User;
import com.zerobase.GameSell.user.exception.UserException;
import com.zerobase.GameSell.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final JwtAuthenticationProvider provider;
  private final UserService userService;

  @GetMapping("/getInfo")
  public ResponseEntity<UserDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token) {

    UserVo vo = provider.getUserVo(token);
    User c = userService.findByIdAndEmail(vo.getId(), vo.getEmail()).orElseThrow(
        () -> new UserException(NOT_FOUND_USER));

    return ResponseEntity.ok(UserDto.from(c));
  }

}
