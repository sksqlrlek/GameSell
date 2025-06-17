package com.zerobase.GameSell.user.config;

import com.user.gamedomain.config.JwtAuthenticationProvider;
import com.user.gamedomain.util.Aes256Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

  private final Aes256Provider aes256Provider;

  public JwtConfig(Aes256Provider aes256Provider) {
    this.aes256Provider = aes256Provider;
  }

  @Bean
  public JwtAuthenticationProvider jwtAuthenticationProvider() {
    return new JwtAuthenticationProvider(aes256Provider);
  }
}
