package com.zerobase.GameSell.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

  @ExceptionHandler({
      UserException.class
  })

  public ResponseEntity<ExceptionResponse> customRequestException(final UserException c) {
    log.warn("api Exception : {}", c.getErrorCode());
    return ResponseEntity.badRequest()
        .body(new ExceptionResponse(c.getMessage(), c.getErrorCode()));
  }

  @Getter
  @ToString
  @AllArgsConstructor
  public static class ExceptionResponse {

    private String message;
    private ErrorCode errorCode;
  }

}
