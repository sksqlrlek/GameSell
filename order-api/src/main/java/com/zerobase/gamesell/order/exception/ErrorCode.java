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
  CART_IS_EMPTY(HttpStatus.BAD_REQUEST, "장바구니에 담긴 게임이 없습니다."),
  AGE_RESTRICTION_VIOLATION(HttpStatus.BAD_REQUEST, "게임의 연령 제한을 만족하지 않습니다."),
  NOT_FOUND_ORDER(HttpStatus.BAD_REQUEST, "주문을 찾을 수 없습니다."),
  NOT_FOUND_PAYMENT(HttpStatus.BAD_REQUEST, "결제 정보를 찾을 수 없습니다."),
  UNPAID_CANNOT_REFUNDED(HttpStatus.BAD_REQUEST, "결제되지 않은 주문은 환불하실 수 없습니다."),
  FORBIDDEN_ORDER_ACCESS(HttpStatus.BAD_REQUEST, "해당 주문에 접근할 수 없습니다."),
  NOT_FOUND_ORDER_ITEM(HttpStatus.BAD_REQUEST, "주문 항목을 찾을 수 없습니다."),
  ALREADY_REFUNDED_ITEM(HttpStatus.BAD_REQUEST, "이미 환불된 게임이 있습니다."),
  INVALID_REFUND_AMOUNT(HttpStatus.BAD_REQUEST, "유효하지 않은 환불 금액입니다."),
  GAME_PRICE_CHANGED(HttpStatus.BAD_REQUEST, "게임의 가격이 변동되었습니다.");

  private final HttpStatus httpStatus;
  private final String detail;
}
