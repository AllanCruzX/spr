package com.allan.spr;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SprApplication implements CommandLineRunner {
	
	//Classe que iniciadora
	//Faz parte da configuração do spring  e defini o ponto de partida para procura dos demias componentes da aplicação.

	public static void main(String[] args) {
		SpringApplication.run(SprApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		

	}

}
