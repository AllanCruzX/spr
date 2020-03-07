package com.allan.spr.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.allan.spr.security.UserSS;

public class UserService {

	public static UserSS authenticated() {
		//Retorna o usuario logado.
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}

	}

}
