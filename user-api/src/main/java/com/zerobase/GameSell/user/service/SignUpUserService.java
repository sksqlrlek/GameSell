package com.zerobase.GameSell.user.service;

import static com.zerobase.GameSell.user.exception.ErrorCode.ALREADY_VERIFY;
import static com.zerobase.GameSell.user.exception.ErrorCode.EXPIRE_CODE;
import static com.zerobase.GameSell.user.exception.ErrorCode.NOT_FOUND_USER;
import static com.zerobase.GameSell.user.exception.ErrorCode.WRONG_VERIFICATION;

import com.zerobase.GameSell.user.domain.SignUpForm;
import com.zerobase.GameSell.user.domain.model.User;
import com.zerobase.GameSell.user.domain.repository.UserRepository;
import com.zerobase.GameSell.user.exception.UserException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpUserService {

  private final UserRepository userRepository;

  public User signUp(SignUpForm form) {
    return userRepository.save(User.from(form));
  }

  public boolean isEmailExist(String email) {
    return userRepository.findByEmail(email.toLowerCase(Locale.ROOT))
        .isPresent();
  }

  public void saveUser(User user) {
    userRepository.save(user);
  }
//  @Transactional
//  public void verifyEmail(String email, String code) {
//    User user = userRepository.findByEmail(email.toLowerCase(Locale.ROOT))
//        .orElseThrow(() -> new UserException(NOT_FOUND_USER));
//    if (user.getVerifiedAt() != null) {
//      throw new UserException(ALREADY_VERIFY);
//    } else if (!user.getVerificationCode().equals(code)) {
//      throw new UserException(WRONG_VERIFICATION);
//    } else if (user.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
//      throw new UserException(EXPIRE_CODE);
//    }
//    user.setVerifiedAt(LocalDateTime.now());
//  }
//
//  @Transactional
//  public LocalDateTime changeUserVerifyExpiredDateTime(Long userId, String verificationCode) {
//    User user = userRepository.findById(userId).orElseThrow(() -> new UserException(NOT_FOUND_USER));
//    user.setVerificationCode(verificationCode);
//    user.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
//    return user.getVerifyExpiredAt();
//  }
}
