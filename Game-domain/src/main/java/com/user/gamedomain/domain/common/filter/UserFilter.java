package com.user.gamedomain.domain.common.filter;

import com.user.gamedomain.config.JwtAuthenticationProvider;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UserFilter implements Filter {

  private final JwtAuthenticationProvider provider;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String token = httpServletRequest.getHeader("X-AUTH-TOKEN");
    log.info("요청된 JWT 토큰: {}", token);

    if (token == null || token.trim().isEmpty()) {
      throw new ServletException("Missing or empty JWT token");
    }

    // 토큰 검증은 이 필터에서는 단순히 유무 체크만 하고
    // 나머지는 필요한 곳에서 provider.getUserVo(token)으로 처리
    chain.doFilter(request, response);
  }
}