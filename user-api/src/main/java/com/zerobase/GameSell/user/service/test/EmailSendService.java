package com.zerobase.GameSell.user.service.test;

import com.zerobase.GameSell.user.client.MailgunClient;
import com.zerobase.GameSell.user.client.mailgun.SendMailForm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendService {
    private final MailgunClient mailgunClient;

    public String sendEmail() {
        SendMailForm form = SendMailForm.builder()
                .from("zerobase-test.my.com")
                .to("sksqlrlek@gmail.com")
                .subject("Test email from zero base")
                .text("my text")
                .build();

        return mailgunClient.sendEmail(form).getBody();
    }
}
