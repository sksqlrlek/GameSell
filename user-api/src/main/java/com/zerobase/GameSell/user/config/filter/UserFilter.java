package com.zerobase.GameSell.user.config.filter;

import com.user.gamedomain.config.JwtAuthenticationProvider;
import com.user.gamedomain.domain.common.UserVo;
import com.zerobase.GameSell.user.service.UserService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;

@WebFilter(urlPatterns = "/user/*")
@RequiredArgsConstructor
public class UserFilter implements Filter {

  private final JwtAuthenticationProvider jwtAuthenticationProvider;
  private final UserService userService;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    String token = req.getHeader("X-AUTH-TOKEN");
    if (!jwtAuthenticationProvider.validateToken(token)) {
      throw new ServletException("Invalid Access");
    }
    UserVo vo = jwtAuthenticationProvider.getUserVo(token);
    userService.findByIdAndEmail(vo.getId(), vo.getEmail())
        .orElseThrow(() -> new ServletException("Invalid Access"));
    chain.doFilter(request, response);
  }
}
