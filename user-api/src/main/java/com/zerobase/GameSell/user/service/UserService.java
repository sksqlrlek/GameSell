package com.zerobase.GameSell.user.service;

import static com.zerobase.GameSell.user.exception.ErrorCode.NOT_FOUND_USER;

import com.zerobase.GameSell.user.domain.model.User;
import com.zerobase.GameSell.user.domain.repository.UserRepository;
import com.zerobase.GameSell.user.exception.UserException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public User findById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new UserException(NOT_FOUND_USER));
  }

  public Optional<User> findByIdAndEmail(Long id, String email) {
    return userRepository.findById(id).stream().filter(user -> user.getEmail()
        .equals(email)).findFirst();
  }

  public Optional<User> findValidUser(String email, String password) {
    return userRepository.findByEmail(email).stream()
        .filter(user -> user.getPassword().equals(password) && user.getVerifiedAt() != null)
        .findFirst();
  }
}
