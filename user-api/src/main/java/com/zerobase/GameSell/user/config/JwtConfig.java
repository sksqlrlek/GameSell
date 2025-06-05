package com.zerobase.GameSell.user.config;

import com.user.gamedomain.config.JwtAuthenticationProvider;
import com.user.gamedomain.util.Aes256Util;
import javax.crypto.SecretKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

  private final Aes256Util aes256Util;

  public JwtConfig(Aes256Util aes256Util) {
    this.aes256Util = aes256Util;
  }

  @Bean
  public JwtAuthenticationProvider jwtAuthenticationProvider() {
    return new JwtAuthenticationProvider(aes256Util);
  }
}
