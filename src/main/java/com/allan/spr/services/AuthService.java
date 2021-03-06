package com.allan.spr.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.allan.spr.domain.Usuario;
import com.allan.spr.repositories.UsuarioRepository;
import com.allan.spr.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private EmailService emailService;

	private Random rand = new Random();

	public void sendNewPassword(String email) {
		//implementando esqueci senha.

		Usuario usuario = usuarioRepository.findByEmail(email);

		if (usuario == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}

		String newPass = newPassword();
		usuario.setSenha(pe.encode(newPass));

		usuarioRepository.save(usuario);
		emailService.sendNewPasswordEmail(usuario, newPass);

	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = rendomChar();
		}
		return new String(vet);
	}

	private char rendomChar() {
		int opt = rand.nextInt(3);

		if (opt == 0) {// gera um digito
			return (char) (rand.nextInt(10) + 48);
		} else if (opt == 1) {// gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		} else {// gera letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}

}
