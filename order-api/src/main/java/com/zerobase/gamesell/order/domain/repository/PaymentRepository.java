package com.zerobase.gamesell.order.domain.repository;

import com.zerobase.gamesell.order.domain.model.Order;
import com.zerobase.gamesell.order.domain.model.Payment;
import com.zerobase.gamesell.order.domain.model.PaymentStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

  List<Payment> findByOrder_UserId(Long userId);

  Optional<Payment> findByOrder(Order order);

  Optional<Payment> findByOrderAndStatus(Order order, PaymentStatus status);
}
