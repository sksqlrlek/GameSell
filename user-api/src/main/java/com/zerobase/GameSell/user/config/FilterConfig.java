package com.zerobase.GameSell.user.config;

import com.user.gamedomain.config.JwtAuthenticationProvider;
import com.user.gamedomain.domain.common.filter.UserFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

  @Bean
  public FilterRegistrationBean<Filter> userFilter(JwtAuthenticationProvider provider) {
    FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new UserFilter(provider));
    registrationBean.addUrlPatterns("/user/*");  // user-api 경로에 맞춰 설정
    registrationBean.setOrder(1);
    return registrationBean;
  }
}
