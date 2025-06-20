package com.zerobase.GameSell.user.service;

import static com.zerobase.GameSell.user.exception.ErrorCode.NOT_FOUND_USER;

import com.zerobase.GameSell.user.domain.SignUpForm;
import com.zerobase.GameSell.user.domain.model.Seller;
import com.zerobase.GameSell.user.domain.model.User;
import com.zerobase.GameSell.user.domain.repository.SellerRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerService {

  private final SellerRepository sellerRepository;

  public Optional<Seller> findById(long id) {
    return sellerRepository.findById(id);
  }

  public void signUpSeller(User user, SignUpForm form) {
    Seller seller = Seller.builder()
        .user(user)
        .company(form.getCompany())
        .bank(form.getBank())
        .number(form.getNumber())
        .registeredDate(LocalDateTime.now())
        .build();

    sellerRepository.save(seller);
  }
}
