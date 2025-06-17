package com.user.gamedomain.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SellerDto {

  private Long id;
  private String email;
  private String company;
  private String bank;
  private String number;

}
