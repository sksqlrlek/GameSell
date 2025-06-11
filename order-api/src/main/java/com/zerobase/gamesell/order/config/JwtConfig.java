package com.zerobase.gamesell.order.config;

import com.user.gamedomain.config.JwtAuthenticationProvider;
import com.user.gamedomain.util.Aes256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

  private final Aes256Util aes256Provider;


  @Bean
  public JwtAuthenticationProvider jwtAuthenticationProvider(Aes256Util aes256Util) {
    return new JwtAuthenticationProvider(aes256Util);
  }
}
