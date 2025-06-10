package br.edu.ifmg.produto.services;

import org.hibernate.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String EmailService;

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(EmailDTO emailDTO) {

        tyr {

                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(EmailService);
                message.setTo(emailDTO.getTo());
                message.setSubject(emailDTO.getSubject());
                message.setText(emailDTO.getText());
                masilSender.send(message);

    }catch (MailSendException e) {
            throw new RuntimeException(e);
        }
    }


}
