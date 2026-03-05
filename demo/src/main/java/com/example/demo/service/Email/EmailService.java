package com.example.demo.service.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpCode(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("resetalldar@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Сброс пароля");
        message.setText("Здравствуйте!\n\nВаш код для подтверждения сброса пароля: " + code +
                "\nКод действителен в течение 5 минут.\n\nЕсли вы не запрашивали сброс, просто игнорируйте это письмо.");

        mailSender.send(message);
    }
}
