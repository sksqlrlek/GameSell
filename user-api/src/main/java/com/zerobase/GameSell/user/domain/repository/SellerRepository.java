package com.zerobase.GameSell.user.domain.repository;

import com.zerobase.GameSell.user.domain.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {


}
