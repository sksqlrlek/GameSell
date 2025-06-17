package com.zerobase.gamesell.order.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @Column(nullable = false, length = 20)
  private String bank;

  @Column(nullable = false, length = 50)
  private String number;

  @Column(name = "amount_paid", nullable = false)
  private Integer amountPaid;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private PaymentStatus status;

  @Column(name = "payment_date", nullable = false)
  private LocalDateTime paymentDate;

  @Column(nullable = false, name = "account_transfer_confirmed")
  private Boolean accountTransferConfirmed;

  @Column(nullable = false, name = "confirm_time")
  private LocalDateTime confirmTime;

  @Column(name = "related_id")
  private Long relatedId;

  @Column(name = "refund_reason", length = 1000)
  private String refundReason;
}
