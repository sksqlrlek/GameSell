package com.zerobase.GameSell.user.application;

import com.user.gamedomain.domain.common.UserType;
//import com.zerobase.GameSell.user.client.MailgunClient;
//import com.zerobase.GameSell.user.client.mailgun.SendMailForm;
import com.zerobase.GameSell.user.domain.SignUpForm;
import com.zerobase.GameSell.user.domain.model.User;
import com.zerobase.GameSell.user.exception.UserException;
import com.zerobase.GameSell.user.exception.ErrorCode;
import com.zerobase.GameSell.user.service.SellerService;
import com.zerobase.GameSell.user.service.SignUpUserService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignUpApplication {

  //  private final MailgunClient mailgunClient;
  private final SignUpUserService signUpUserService;
  private final SellerService sellerService;

//  public void userVerify(String email, String code) {
//    signUpUserService.verifyEmail(email, code);
//  }

  public String userSignUp(SignUpForm form) {
    if (signUpUserService.isEmailExist(form.getEmail())) {
      throw new UserException(ErrorCode.ALREADY_REGISTER_USER);
      // exception
    } else {
      User c = signUpUserService.signUp(form);

      if (c.getRole() == UserType.SELLER) {
        sellerService.signUpSeller(c, form);
      }
//
//      String code = getRandomCode();
//      SendMailForm sendMailForm = SendMailForm.builder()
//          .from("sksqlrlek@sandbox1e7f881466ce40cf9c183f672eaabbb5.mailgun.org")
//          .to(form.getEmail())
//          .subject("Verification Email!")
//          .text(getVerificationEmailBody(c.getEmail(), code))
//          .build();
//      log.info("Send email result : " + mailgunClient.sendEmail(sendMailForm).getBody());
//
//      signUpUserService.changeUserVerifyExpiredDateTime(c.getId(), code);
      c.setVerifiedAt(LocalDateTime.now());
      signUpUserService.saveUser(c);
      return "회원 가입에 성공하였습니다.";
    }
  }

//  private String getRandomCode() {
//    return RandomStringUtils.random(10, true, true);
//  }
//
//  private String getVerificationEmailBody(String email, String code) {
//    StringBuilder builder = new StringBuilder();
//    return builder.append("Hello ").append("! Please Click Link for verification\n\n")
//        .append("http://localhost:8081/signup/verify/user?email=")
//        .append(email)
//        .append("&code=")
//        .append(code).toString();
//  }


}
