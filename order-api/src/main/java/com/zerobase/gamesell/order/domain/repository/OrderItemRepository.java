package com.zerobase.gamesell.order.domain.repository;

import com.zerobase.gamesell.order.domain.model.Order;
import com.zerobase.gamesell.order.domain.model.OrderItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
  List<OrderItem> findAllByOrder(Order order);

}
