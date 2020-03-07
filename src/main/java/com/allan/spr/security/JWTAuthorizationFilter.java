package com.allan.spr.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
	private JWTUtil jwtUtil;
	
	private UserDetailsService userDetailsService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager , JWTUtil jwtUtil ,UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//Aqui vou  recuperar o token no cabeçalho, validá-lo, e se estiver ok autentificar o usuário para o Spring. 
				//Toda requisição Http vou precisar autenticar por que não existe mas o conceito de manter o usuario em sessão não existe usuario logado .
		String header = request.getHeader("Authorization");//Nome do cabecalho  - Authorization
		
		if(header != null && header.startsWith("Bearer ")) {
			UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));
			if(auth != null) {
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		chain.doFilter(request, response);
	}


	private UsernamePasswordAuthenticationToken getAuthentication( String token) {
		if(jwtUtil.tokenValido(token)) {
			//valida token e retorna o usuario
			String username = jwtUtil.getUsername(token);
			UserDetails user = userDetailsService.loadUserByUsername(username);//busca no banco de dados o usuario.
			return new UsernamePasswordAuthenticationToken(user, null , user.getAuthorities());//autorizando usuario por perfil
			
		}
		return null;
	}

}
