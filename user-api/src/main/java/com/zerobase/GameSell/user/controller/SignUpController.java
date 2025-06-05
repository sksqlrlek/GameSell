package com.zerobase.GameSell.user.controller;

import com.zerobase.GameSell.user.application.SignUpApplication;
import com.zerobase.GameSell.user.domain.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {

  private final SignUpApplication signUpApplication;

  @PostMapping
  public ResponseEntity<String> customerSignUp(@RequestBody SignUpForm form) {
    return ResponseEntity.ok(signUpApplication.userSignUp(form));
  }

  @GetMapping("/verify/user")
  public ResponseEntity<String> verifyCustomer(String email, String code) {
    signUpApplication.userVerify(email, code);
    return ResponseEntity.ok("인증이 완료되었습니다.");
  }
}
