package com.allan.spr.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.allan.spr.domain.Usuario;

public interface EmailService {

	void sendOrderConfirmatioEmail(String email);

	void sendEmail(SimpleMailMessage msg);

	void sendOrderConfirmationHtmlEmail(String email);

	void sendHtmlEmail(MimeMessage msg);

	void sendNewPasswordEmail(Usuario usuario, String newPass);

}
