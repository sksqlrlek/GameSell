package com.zerobase.gamesell.order.controller;

import com.zerobase.gamesell.order.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping("/confirm")
  public ResponseEntity<String> confirmPayment(@RequestParam Long paymentId) {
    paymentService.confirmPayment(paymentId);
    return ResponseEntity.ok("입금이 확인되었습니다.");
  }

}
