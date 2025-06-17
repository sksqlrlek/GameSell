package com.zerobase.gamesell.order.domain.repository;

import com.zerobase.gamesell.order.domain.redis.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {

}
