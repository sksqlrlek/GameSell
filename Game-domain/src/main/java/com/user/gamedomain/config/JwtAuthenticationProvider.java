package com.user.gamedomain.config;

import com.user.gamedomain.domain.common.UserVo;
import com.user.gamedomain.domain.common.UserType;
import com.user.gamedomain.util.Aes256Provider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

@Slf4j
@Component
public class JwtAuthenticationProvider {

  private final Aes256Provider aes256Provider;

  @Value("${jwt.secret}")
  private String secretKey;

  private long tokenValidTime = 1000L * 60 * 60 * 24;

  public JwtAuthenticationProvider(Aes256Provider aes256Provider) {
    this.aes256Provider = aes256Provider;
  }

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
  }

  public String createToken(String userPk, Long id, UserType userType) {
    System.out.println("✅ createToken 호출됨");

    Claims claims = Jwts.claims().setSubject(aes256Provider.encrypt(userPk))
        .setId(aes256Provider.encrypt(id.toString()));
    claims.put("roles", userType);
    Date now = new Date();
    log.info("JWT 발급 - secretKey (bytes): {}",
        Arrays.toString(secretKey.getBytes(StandardCharsets.UTF_8)));

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + tokenValidTime))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean isValid(String jwtToken) {
    try {
      Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
          .parseClaimsJws(jwtToken);
      return !claimsJws.getBody().getExpiration().before(new Date());
    } catch (Exception e) {
      return false;
    }
  }

  public boolean validateToken(String token) {
    System.out.println("✅ validateToken 호출됨");

    try {
      log.info("JWT 검증 - secretKey (bytes): {}",
          Arrays.toString(secretKey.getBytes(StandardCharsets.UTF_8)));

      Jwts.parserBuilder()
          .setSigningKey(getSigningKey())
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      log.error("JWT validation failed", e);
      return false;

    }
  }

  public UserVo getUserVo(String token) {
    log.info("전달받은 토큰: {}", token);

    Claims c = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
        .getBody();
    log.info("Claims: {}", c);

    String decryptedIdStr = aes256Provider.decrypt(c.getId());

    if (decryptedIdStr == null || decryptedIdStr.isEmpty()) {
      throw new RuntimeException("복호화된 ID가 유효하지 않습니다.");
    }

    Long id = Long.valueOf(decryptedIdStr);

    String decryptedEmail = aes256Provider.decrypt(c.getSubject());
    System.out.println("복호화된 이메일: " + decryptedEmail);
    return new UserVo(id, decryptedEmail);

  }

//  public String createToken(String email, Long id, UserType role) {
//  }
}