package com.zerobase.GameSell.user.client.mailgun;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class SendMailForm {

  private String from;
  private String to;
  private String subject;
  private String text;
}
