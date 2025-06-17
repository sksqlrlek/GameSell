package com.zerobase.gamesell.order.domain.dto;

import com.zerobase.gamesell.order.domain.model.PaymentStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

  private Long paymentId;
  private Long orderId;
  private Integer amountPaid;
  private PaymentStatus status;
  private LocalDateTime paymentDate;
  private String bank;
  private String number;
  private boolean refunded;
  private boolean confirmed;
}
