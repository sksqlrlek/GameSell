package com.zerobase.gamesell.order.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
  NOT_FOUND_GAME(HttpStatus.BAD_REQUEST, "게임을 찾을 수 없습니다.");

  private final HttpStatus httpStatus;
  private final String detail;
}
