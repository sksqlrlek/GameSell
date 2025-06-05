package com.zerobase.GameSell.user.domain.Dto;

import com.zerobase.GameSell.user.domain.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SellerDto {

  private String company;
  private String bank;
  private String number;

  public static SellerDto from(Seller seller) {
    return new SellerDto(
        seller.getCompany(),
        seller.getBank(),
        seller.getNumber());
  }
}
