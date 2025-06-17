package com.zerobase.GameSell.user.controller;

import static com.zerobase.GameSell.user.exception.ErrorCode.NOT_FOUND_USER;

import com.user.gamedomain.config.JwtAuthenticationProvider;
import com.user.gamedomain.domain.dto.SellerDto;
import com.user.gamedomain.domain.common.UserVo;
import com.zerobase.GameSell.user.domain.model.Seller;
import com.zerobase.GameSell.user.domain.repository.SellerDtoMapper;
import com.zerobase.GameSell.user.exception.UserException;
import com.zerobase.GameSell.user.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {

  private final SellerService sellerService;
  private final JwtAuthenticationProvider provider;

  @GetMapping("/getInfo")
  public ResponseEntity<SellerDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
    UserVo vo = provider.getUserVo(token);
    Seller seller = sellerService.findById(vo.getId())
        .orElseThrow(() -> new UserException(NOT_FOUND_USER));
    return ResponseEntity.ok(SellerDtoMapper.from(seller));
  }
}
