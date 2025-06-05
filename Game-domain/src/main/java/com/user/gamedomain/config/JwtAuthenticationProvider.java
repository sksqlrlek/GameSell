package com.user.gamedomain.config;

import com.user.gamedomain.domain.common.UserVo;
import com.user.gamedomain.domain.common.UserType;
import com.user.gamedomain.util.Aes256Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtAuthenticationProvider {

  private final Aes256Util aes256Util;

  @Value("${jwt.secret}")
  private String secretKey;

  private long tokenValidTime = 1000L * 60 * 60 * 24;

  public JwtAuthenticationProvider(Aes256Util aes256Util) {
    this.aes256Util = aes256Util;
  }

  public String createToken(String userPk, Long id, UserType userType) {
    Claims claims = Jwts.claims().setSubject(aes256Util.encrypt(userPk))
        .setId(aes256Util.encrypt(id.toString()));
    claims.put("roles", userType);
    Date now = new Date();
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + tokenValidTime))
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  public boolean isValid(String jwtToken) {
    try {
      Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
      return !claimsJws.getBody().getExpiration().before(new Date());
    } catch (Exception e) {
      return false;
    }
  }

  public UserVo getUserVo(String token) {
    Claims c = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    String decryptedIdStr = aes256Util.decrypt(c.getId());
    System.out.println("복호화된 ID 문자열: " + decryptedIdStr);

    if (decryptedIdStr == null || decryptedIdStr.isEmpty()) {
      throw new RuntimeException("복호화된 ID가 유효하지 않습니다.");
    }

    Long id = Long.valueOf(decryptedIdStr);

    String decryptedEmail = aes256Util.decrypt(c.getSubject());
    System.out.println("복호화된 이메일: " + decryptedEmail);

    return new UserVo(id, decryptedEmail);

  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser()
          .setSigningKey(secretKey)
          .parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

//  public String createToken(String email, Long id, UserType role) {
//  }
}