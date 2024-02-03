package br.com.javaarquiteto.infrastructure.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;



@Component
public class MailSenderComponent {
	
	private static final Logger LOGGER = LogManager.getLogger(MailSenderComponent.class);
	
	@Autowired
	JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	String userName;
	
	public void sendMessage(String to, String subject, String body) {		
		
		LOGGER.info("Inicio envio de e-mail");
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		
		try {
			
			helper.setFrom(userName);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body,true);
			javaMailSender.send(mimeMessage);
			
		} catch (MessagingException e) {
			LOGGER.error("Falha ao enviar e-mail.", e.getMessage());
		}finally {
			LOGGER.info("Fim envio de e-mail");
		}
		
		
		
	}

}
