package com.zerobase.gamesell.order.config;

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
    registrationBean.addUrlPatterns("/user/*", "/order/*", "/cart/*"); // 필요한 URI 패턴 등록
    registrationBean.setOrder(1); // 필터 순서 지정 (낮을수록 먼저 실행됨)
    return registrationBean;
  }
}
