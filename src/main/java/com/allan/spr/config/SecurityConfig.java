package com.allan.spr.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.allan.spr.security.JWTAuthenticationFilter;
import com.allan.spr.security.JWTAuthorizationFilter;
import com.allan.spr.security.JWTUtil;

@Configuration//classe de configuração
@EnableWebSecurity//habilito a configuração do spring security
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private Environment env;

	@Autowired
	private JWTUtil jwtUtil;

	private static final String[] PUBLIC_MATCHERS = { "/h2-console/**"

	};

	private static final String[] PUBLIC_MATCHERS_GET = { "/alunos/** ", "/voluntarios/**" , "/atividades/**"

	};
	
	private static final String[] PUBLIC_MATCHERS_PUT = { "/alunos/", "/voluntarios/**" , "/atividades/**"

	};
	
	private static final String[] PUBLIC_MATCHERS_DELETE = { "/alunos/", "/voluntarios/**" , "/atividades/**"

	};

	private static final String[] PUBLIC_MATCHERS_POST = { "/alunos/**","/voluntarios/**" ,"/atividades/**" ,"/auth/forgot/**"

	};

	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		//.anyRequest().authenticated() - A URI PRECISA ESTÀ AUTENTICADA.
		//.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll().antMatchers(PUBLIC_MATCHERS).permitAll() - A URI NÃO PRECISA EStÁ AUTENTICADA. 
		//Csrf é uma abreviação para cross-site request forgery, que é um tipo de ataque hacker que acontece em aplicações web. Como vamos fazer autenticação via token, automaticamente nossa API está livre desse tipo de ataque
		//http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); - Estou dizendo nessa linha para não cria sessão que é para ele ser STATELESS.
		
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			//se for o profile de teste vou acessar o bando h2.
			//Liberar o acesso ao H2.
			// http.headers().frameOptions().disable();
			http.headers().frameOptions().sameOrigin();
		}

		http.cors().and().csrf().disable();
		http.authorizeRequests().antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
				.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll().antMatchers(PUBLIC_MATCHERS).permitAll()
				.antMatchers(HttpMethod.PUT, PUBLIC_MATCHERS_PUT).permitAll().antMatchers(PUBLIC_MATCHERS).permitAll()
				.antMatchers(HttpMethod.DELETE, PUBLIC_MATCHERS_DELETE).permitAll().antMatchers(PUBLIC_MATCHERS).permitAll()
				.anyRequest().authenticated();
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));//filtro de autenticação.
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));//filtro de autorização.
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		//codicar a senha
		//Configuraçãoes de autenticação
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());

	}

	@Bean  // Com o @Bean eu consigo ijetar o metodo.
	//Cors permite que uma página da Web solicite recursos adicionais no navegador de outros domínios, como fontes, CSS ou imagens estáticas da CDN.
	CorsConfigurationSource corsConfigurationSource() {
		// CORS garantir a segurança na negação - compartilhamento de recursos com origem diferentes. carregar conteudo de uma URL sem passar pela barra de endereço. ((protocolo)->https://meuhost.com->(host):80 -> (porta)) ->origem
		//Não deixar que o javascript aceese conteudo de fora da requisição 
		//garantir que as requisições sejan feitas para essa API.
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPITIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean//vou poder injetar em qual quer classe do meu sistema
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		//encriptar senha 
		return new BCryptPasswordEncoder();

	}
}
