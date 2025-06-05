package com.zerobase.GameSell.user;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ServletComponentScan
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.zerobase.GameSell.user", "com.user.gamedomain"})
@EnableJpaAuditing
@EnableJpaRepositories
@RequiredArgsConstructor
public class UserApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserApplication.class, args);

  }
}
