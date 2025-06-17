package com.zerobase.gamesell.order.domain.model;

public enum PaymentStatus {
  PENDING, // 입금 대기중
  PAID, // 결제됨
  REFUND, // 환불
  FAILED // 실패, 취소
}
