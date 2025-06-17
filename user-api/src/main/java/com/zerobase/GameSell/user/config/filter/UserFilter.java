//package com.zerobase.GameSell.user.config.filter;
//
//import com.user.gamedomain.config.JwtAuthenticationProvider;
//import com.user.gamedomain.domain.common.UserVo;
//import com.zerobase.GameSell.user.service.UserService;
//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@WebFilter(urlPatterns = "/user/*")
//@RequiredArgsConstructor
//public class UserFilter implements Filter {
//
//  private final JwtAuthenticationProvider jwtAuthenticationProvider;
//  private final UserService userService;
//
//  @Override
//  public void doFilter(ServletRequest request, ServletResponse response,
//      FilterChain chain) throws IOException, ServletException {
//    HttpServletRequest req = (HttpServletRequest) request;
//    String token = req.getHeader("X-AUTH-TOKEN");
//
//    log.info("요청된 JWT 토큰: {}", token);  // 추가
//    if (token == null || token.isBlank()) {
//      throw new ServletException("Missing or empty JWT token");
//    }
//    if (!jwtAuthenticationProvider.validateToken(token)) {
//      throw new ServletException("Invalid Access");
//    }
//    UserVo vo = jwtAuthenticationProvider.getUserVo(token);
//    log.info("UserVo from token - id: {}, email: {}", vo.getId(), vo.getEmail());
//    var optionalUser = userService.findByIdAndEmail(vo.getId(), vo.getEmail());
//    if (optionalUser.isEmpty()) {
//      log.error("유저 정보 없음 - id: {}, email: {}", vo.getId(), vo.getEmail());
//      throw new ServletException("Invalid Access");
//    }
//    chain.doFilter(request, response);
//  }
//}
