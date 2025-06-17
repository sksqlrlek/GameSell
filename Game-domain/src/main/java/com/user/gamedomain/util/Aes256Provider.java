package com.user.gamedomain.util;

import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Aes256Provider {

  private final String alg;
  private final String KEY;
  private final String IV;

  public Aes256Provider(@Value("${aes256.alg}") String alg,
      @Value("${aes256.key}") String KEY) {
    this.alg = alg;
    this.KEY = KEY;
    this.IV = KEY.substring(0, 16);
  }

  public String encrypt(String text) {
    try {
      Cipher cipher = Cipher.getInstance(alg);
      SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
      IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
      cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
      byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
      return Base64.encodeBase64String(encrypted);
    } catch (Exception e) {
      log.error("암호화 중 오류 발생", e);
      throw new RuntimeException("암호화 실패");
    }
  }

  public String decrypt(String cipherText) {
    try {
      Cipher cipher = Cipher.getInstance(alg);
      SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
      IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
      cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

      byte[] decodedBytes = Base64.decodeBase64(cipherText);
      byte[] decrypted = cipher.doFinal(decodedBytes);
      return new String(decrypted, StandardCharsets.UTF_8);

    } catch (Exception e) {
      log.error("복호화 중 오류 발생", e);
      throw new RuntimeException("복호화 실패");
    }
  }

}
