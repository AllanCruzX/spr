package com.allan.spr.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.allan.spr.domain.Usuario;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	@Override
	public void sendOrderConfirmatioEmail(String email) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(email);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(String email) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(email);
		sm.setFrom(sender);
		sm.setSubject("Email enviado" );
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(email);
		return sm;
	}
	
	protected String htmlFromTemplatePedido(String email) {
		Context context = new Context();
		context.setVariable("pedido",email);		
		return templateEngine.process("email/comfirmacaoPedido", context);
		
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(String email) {
		
		try {
			MimeMessage mm = prepareMimeMessageFromPedido(email);
			sendHtmlEmail(mm);
		} catch (MessagingException e) {
			sendOrderConfirmatioEmail(email);
		}
		
		
	}

	private MimeMessage prepareMimeMessageFromPedido(String email) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(email);
		mmh.setFrom(sender);
		mmh.setSubject("Email enviado" );
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(email), true);
		
		
		return mimeMessage;
	}
	
	@Override
	public void sendNewPasswordEmail(Usuario usuario, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(usuario, newPass);
		sendEmail(sm);
		
	}

	protected  SimpleMailMessage prepareNewPasswordEmail(Usuario usuario, String newPass) {
		
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(usuario.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha" );
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha" + newPass);
		return sm;
		
	}
	

}
