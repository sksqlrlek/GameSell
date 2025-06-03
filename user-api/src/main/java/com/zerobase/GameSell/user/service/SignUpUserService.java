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

  @Transactional
  public void verifyEmail(String email, String code) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UserException(NOT_FOUND_USER));
    if (user.isVerify()) {
      throw new UserException(ALREADY_VERIFY);
    } else if (!user.getVerificationCode().equals(code)) {
      throw new UserException(WRONG_VERIFICATION);
    } else if (user.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
      throw new UserException(EXPIRE_CODE);
    }
    user.setVerify(true);
  }

  @Transactional
  public LocalDateTime changeUserValidateEmail(Long userId, String verificationCode) {
    Optional<User> UserOptional = userRepository.findById(userId);

    if (UserOptional.isPresent()) {
      User user = UserOptional.get();
      user.setVerificationCode(verificationCode);
      user.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
      return user.getVerifyExpiredAt();
    }
    //todo : 예외 처리
    throw new UserException(NOT_FOUND_USER);
  }
}
