package com.zerobase.GameSell.user.application;

import com.user.gamedomain.config.JwtAuthenticationProvider;
import com.user.gamedomain.domain.common.UserType;
import com.zerobase.GameSell.user.domain.SignInForm;
import com.zerobase.GameSell.user.domain.model.User;
import com.zerobase.GameSell.user.exception.UserException;
import com.zerobase.GameSell.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.zerobase.GameSell.user.exception.ErrorCode.LOGIN_CHECK_FAIL;

@Service
@RequiredArgsConstructor
public class SignInApplication {

  private final UserService userService;
  private final JwtAuthenticationProvider provider;

  public String userLoginToken(SignInForm form) {
    // 1. 로그인 가능 여부
    User c = userService.findValidUser(form.getEmail(), form.getPassword())
        .orElseThrow(() -> new UserException(LOGIN_CHECK_FAIL));
    // 2. 토큰 발행

    // 3. 토큰 response
    return provider.createToken(c.getEmail(), c.getId(), c.getRole());
  }

  public String sellerLoginToken(SignInForm form) {
    User s = userService.findValidUser(form.getEmail(), form.getPassword())
        .orElseThrow(() -> new UserException(LOGIN_CHECK_FAIL));

    if (s.getRole() != UserType.SELLER) {
      throw new UserException(LOGIN_CHECK_FAIL);
    }

    return provider.createToken(s.getEmail(), s.getId(), s.getRole());
  }

}
