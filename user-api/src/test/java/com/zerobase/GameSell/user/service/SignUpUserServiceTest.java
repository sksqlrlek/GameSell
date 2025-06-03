package com.zerobase.GameSell.user.service;

import static org.junit.jupiter.api.Assertions.*;

import com.zerobase.GameSell.user.domain.SignUpForm;
import com.zerobase.GameSell.user.domain.model.User;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SignUpUserServiceTest {

  @Autowired
  private SignUpUserService service;

  @Test
  void signUp() {
    SignUpForm form = SignUpForm.builder()
        .name("name")
        .birth(LocalDate.now())
        .email("sksqlrlek@gmail.com")
        .password("1")
        .phone("0100000000")
        .build();
    User c = service.signUp(form);
    assertNotNull(c.getId());
    assertNotNull(c.getCreatedAt());

  }
}