package com.zerobase.GameSell.user.domain.repository;

import com.user.gamedomain.domain.Dto.SellerDto;
import com.zerobase.GameSell.user.domain.model.Seller;

public class SellerDtoMapper {

  public static SellerDto from(Seller seller) {
    return new SellerDto(seller.getCompany(), seller.getBank(), seller.getNumber());
  }

}
