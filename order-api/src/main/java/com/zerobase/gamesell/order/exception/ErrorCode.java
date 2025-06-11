package com.zerobase.gamesell.order.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
  NOT_FOUND_GAME(HttpStatus.BAD_REQUEST, "게임을 찾을 수 없습니다."),
  CART_CHANGE_FAIL(HttpStatus.BAD_REQUEST, "장바구니에 추가할 수 없습니다."),
  ALREADY_IN_CART(HttpStatus.BAD_REQUEST, "이미 장바구니에 담겨있습니다."),
  AGE_RESTRICTION_VIOLATION(HttpStatus.BAD_REQUEST, "게임의 연령 제한을 만족하지 않습니다."),
  GAME_PRICE_CHANGED(HttpStatus.BAD_REQUEST, "게임의 가격이 변동되었습니다.")
  ;

  private final HttpStatus httpStatus;
  private final String detail;
}
