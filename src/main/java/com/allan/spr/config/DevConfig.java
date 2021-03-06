package com.allan.spr.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.allan.spr.services.EmailService;
import com.allan.spr.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	//Com o @Bean eu posso injetar a classe e usar o metodo.
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		//Só executa se a configuração do hibernate estiver como create.
		
		if(!"create".equals(strategy)) {
			return false;
		}
		
	
		 return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}

}
