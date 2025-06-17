package com.zerobase.gamesell.order.service;

import static com.zerobase.gamesell.order.exception.ErrorCode.*;

import com.zerobase.gamesell.order.domain.dto.PaymentResponse;
import com.zerobase.gamesell.order.domain.model.Order;
import com.zerobase.gamesell.order.domain.model.OrderItem;
import com.zerobase.gamesell.order.domain.model.OrderStatus;
import com.zerobase.gamesell.order.domain.model.Payment;
import com.zerobase.gamesell.order.domain.model.PaymentStatus;
import com.zerobase.gamesell.order.domain.repository.OrderRepository;
import com.zerobase.gamesell.order.domain.repository.PaymentRepository;
import com.zerobase.gamesell.order.exception.CustomException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

  private final PaymentRepository paymentRepository;
  private final OrderRepository orderRepository;

  @Transactional
  public Payment createBankTransferPayment(Order order, String bank, String accountNumber) {
    Payment payment = Payment.builder()
        .order(order)
        .bank(bank)
        .number(accountNumber)
        .amountPaid(order.getPrice())
        .status(PaymentStatus.PENDING)
        .paymentDate(LocalDateTime.now())
        .confirmTime(LocalDateTime.now())
        .accountTransferConfirmed(false)
        .build();
    return paymentRepository.save(payment);
  }

  @Transactional
  public void confirmPayment(Long paymentId) {
    Payment payment = paymentRepository.findById(paymentId)
        .orElseThrow(() -> new RuntimeException("결제를 찾을 수 없습니다."));

    payment.setStatus(PaymentStatus.PAID);
    payment.setAccountTransferConfirmed(true);
    payment.setConfirmTime(LocalDateTime.now());

    Order order = payment.getOrder();
    order.setOrderStatus(OrderStatus.PAID);

    paymentRepository.save(payment);
  }

  public List<PaymentResponse> getPaymentsByUser(Long userId) {
    List<Payment> payments = paymentRepository.findByOrder_UserId(userId);

    return payments.stream()
        .map(p -> PaymentResponse.builder()
            .paymentId(p.getId())
            .orderId(p.getOrder().getId())
            .amountPaid(p.getAmountPaid())
            .status(p.getStatus())
            .paymentDate(p.getPaymentDate())
            .bank(p.getBank())
            .number(p.getNumber())
            .refunded(p.getStatus() == PaymentStatus.REFUND)
            .confirmed(p.getAccountTransferConfirmed())
            .build())
        .toList();
  }

  @Transactional
  public Payment refundPayment(Long orderId, String refundReason) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new CustomException(NOT_FOUND_ORDER));

    if (!order.getOrderStatus().equals(OrderStatus.PAID)) {
      throw new CustomException(UNPAID_CANNOT_REFUNDED);
    }

    Payment originalPayment = paymentRepository.findByOrder(order)
        .orElseThrow(() -> new CustomException(NOT_FOUND_PAYMENT));

    originalPayment.setStatus(PaymentStatus.REFUND);
    originalPayment.setAccountTransferConfirmed(false);

    Payment refundPayment = Payment.builder()
        .order(order)
        .bank(originalPayment.getBank())
        .number(originalPayment.getNumber())
        .amountPaid(originalPayment.getAmountPaid() * -1)
        .status(PaymentStatus.REFUND)
        .paymentDate(LocalDateTime.now())
        .accountTransferConfirmed(true)
        .confirmTime(LocalDateTime.now())
        .relatedId(originalPayment.getId())
        .refundReason(refundReason)
        .build();

    order.setOrderStatus(OrderStatus.CANCELLED);

    paymentRepository.save(originalPayment);
    return paymentRepository.save(refundPayment);
  }

  @Transactional
  public Payment refundPartial(Long orderId, List<Long> orderItemIds, String reason) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new CustomException(NOT_FOUND_ORDER));

    List<OrderItem> itemsToRefund = order.getOrderItems().stream()
        .filter(item -> orderItemIds.contains(item.getId())).toList();

    if (itemsToRefund.isEmpty()) {
      throw new CustomException(NOT_FOUND_ORDER_ITEM);
    }

    int refundAmount = 0;

    for (OrderItem item : itemsToRefund) {
      if (item.isRefunded()) {
        throw new CustomException(ALREADY_REFUNDED_ITEM);
      }
      refundAmount += item.getPrice();
      item.setRefunded(true);
    }

    if (refundAmount <= 0) {
      throw new CustomException(INVALID_REFUND_AMOUNT);
    }

    Payment originalPayment = paymentRepository.findByOrderAndStatus(
            order, PaymentStatus.PAID)
        .orElseThrow(() -> new CustomException(NOT_FOUND_PAYMENT));

    Payment refundPayment = Payment.builder()
        .order(order)
        .bank(originalPayment.getBank())
        .number(originalPayment.getNumber())
        .amountPaid(refundAmount * -1)
        .status(PaymentStatus.REFUND)
        .paymentDate(LocalDateTime.now())
        .accountTransferConfirmed(true)
        .confirmTime(LocalDateTime.now())
        .relatedId(originalPayment.getId())
        .refundReason(reason)
        .build();

    order.setPrice(order.getPrice() - refundAmount);

    if (order.getPrice() == 0) {
      order.setOrderStatus(OrderStatus.CANCELLED);
    }

    return paymentRepository.save(refundPayment);
  }
}
