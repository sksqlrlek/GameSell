package com.zerobase.gamesell.order.config;

import com.user.gamedomain.config.JwtAuthenticationProvider;
import com.user.gamedomain.util.Aes256Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {


  @Bean
  public JwtAuthenticationProvider jwtAuthenticationProvider(Aes256Provider aes256Provider) {
    return new JwtAuthenticationProvider(aes256Provider);
  }
}
