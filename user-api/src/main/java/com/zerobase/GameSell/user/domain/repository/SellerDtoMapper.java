package com.zerobase.GameSell.user.domain.repository;

import com.user.gamedomain.domain.dto.SellerDto;
import com.zerobase.GameSell.user.domain.model.Seller;

public class SellerDtoMapper {

  public static SellerDto from(Seller seller) {
    return new SellerDto(seller.getUser().getId(), seller.getUser().getEmail(), seller.getCompany(),
        seller.getBank(), seller.getNumber());
  }

}
