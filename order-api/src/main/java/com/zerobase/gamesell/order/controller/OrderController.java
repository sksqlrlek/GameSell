package com.zerobase.gamesell.order.controller;

import static com.zerobase.gamesell.order.exception.ErrorCode.*;

import com.user.gamedomain.config.JwtAuthenticationProvider;
import com.zerobase.gamesell.order.domain.dto.OrderResponse;
import com.zerobase.gamesell.order.domain.dto.PaymentResponse;
import com.zerobase.gamesell.order.domain.dto.RefundRequest;
import com.zerobase.gamesell.order.domain.model.Order;
import com.zerobase.gamesell.order.domain.model.Payment;
import com.zerobase.gamesell.order.exception.CustomException;
import com.zerobase.gamesell.order.exception.ErrorCode;
import com.zerobase.gamesell.order.service.OrderService;
import com.zerobase.gamesell.order.service.PaymentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;
  private final JwtAuthenticationProvider provider;
  private final PaymentService paymentService;

  @PostMapping("/create")
  public ResponseEntity<String> createOrder(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
    Long userId = provider.getUserVo(token).getId();
    Order order = orderService.createOrderFromCart(userId);
    Payment payment = paymentService.createBankTransferPayment(order, "KB", "112-33-3333");

    String message = String.format("주문 완료. \n[입금 안내]\n은행명: %s\n계좌번호: %s\n입금 금액: %d원",
        payment.getBank(), payment.getNumber(), payment.getAmountPaid());
    return ResponseEntity.ok(message);
  }

  @GetMapping
  public ResponseEntity<List<OrderResponse>> getMyorders(
      @RequestHeader(name = "X-AUTH-TOKEN") String token) {
    Long userId = provider.getUserVo(token).getId();
    List<OrderResponse> orders = orderService.getOrderByUser(userId);
    return ResponseEntity.ok(orders);
  }

  @GetMapping("/payment")
  public ResponseEntity<List<PaymentResponse>> getMyPayments(
      @RequestHeader(name = "X-AUTH-TOKEN") String token) {
    Long userId = provider.getUserVo(token).getId();
    return ResponseEntity.ok(paymentService.getPaymentsByUser(userId));
  }

  @PostMapping("{orderId}/refund")
  public ResponseEntity<String> refundOrder(
      @RequestHeader(name = "X-AUTH-TOKEN") String token,
      @PathVariable Long orderId,
      @RequestParam String reason) {
    Long userId = provider.getUserVo(token).getId();
    Order order = orderService.getOrderById(orderId);

    if (!order.getUserId().equals(userId)) {
      throw new CustomException(FORBIDDEN_ORDER_ACCESS);
    }

    Payment refunded = paymentService.refundPayment(orderId, reason);

    return ResponseEntity.ok(
        String.format("환불 완료: %d원, 사유: %s", refunded.getAmountPaid() * -1,
            refunded.getRefundReason()));
  }

  @PostMapping("{orderId}/partial-refund")
  public ResponseEntity<String> partialRefundOrder(
      @RequestHeader(name = "X-AUTH-TOKEN") String token,
      @PathVariable Long orderId,
      @RequestBody RefundRequest request
  ) {
    Long userId = provider.getUserVo(token).getId();
    Order order = orderService.getOrderById(orderId);

    if (!order.getUserId().equals(userId)) {
      throw new CustomException(FORBIDDEN_ORDER_ACCESS);
    }

    Payment refundPayment = paymentService.refundPartial(orderId, request.getOrderItemIds(),
        request.getRefundReason());

    return ResponseEntity.ok(
        String.format("부분 환불 완료: %d원, 사유: %s", refundPayment.getAmountPaid() * -1,
            refundPayment.getRefundReason())
    );
  }
}
