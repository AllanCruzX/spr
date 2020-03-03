package com.allan.spr.services;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.allan.spr.domain.Cidade;
import com.allan.spr.domain.Endereco;
import com.allan.spr.domain.Estado;
import com.allan.spr.domain.Usuario;
import com.allan.spr.domain.enums.Perfil;
import com.allan.spr.domain.enums.StAtivo;
import com.allan.spr.domain.enums.StSimNao;
import com.allan.spr.repositories.CidadeRepository;
import com.allan.spr.repositories.EnderecoRepository;
import com.allan.spr.repositories.EstadoRepository;
import com.allan.spr.repositories.UsuarioRepository;

@Service
public class DBService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public void instantiateTestDatabase() throws ParseException {
		
		Estado est1 = new Estado();
		est1.setNome("Bahia");
		Estado est2 = new Estado();
		est2.setNome("São Paulo");

		Cidade c1 = new Cidade();
		c1.setNome("Salvador");
		c1.setEstado(est1);
		
		Cidade c2 = new Cidade();
		c2.setNome("São Paulo");
		c2.setEstado(est2);
		
		Cidade c3 = new Cidade();
		c3.setNome("Campinas");
		c3.setEstado(est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		
		Usuario usu = new Usuario();
		
		usu.setNome("IVAN LIMA");
		usu.setCpf("00544141059");
		usu.setNomeMae("BARBARA LIMA");
		usu.setNomePai("JOAQUIM LIMA");
		usu.setNomeResponsavel("BARBARA LIMA");
		
		//LocalDate niver =  LocalDate.of(2010, 11, 6);
		DateFormat f = DateFormat.getDateInstance();
		
		usu.setDataNsacimento(f.parse("12/01/1995"));
		usu.setTelefone("33820552");
		usu.setEmail("INAVAN@GMAIL.COM");
		usu.setPcd(StSimNao.NAO);
		usu.setSt(StAtivo.ATIVO);
		usu.addPerfil(Perfil.ALUNO);
		
		
		usu.setEndereco(new Endereco());
		usu.getEndereco().setCep("4150020");
		usu.getEndereco().setBairro("SUSUARANA");
		usu.getEndereco().setLogradouro("JAMAICA");
		usu.getEndereco().setNumero("12");
		usu.getEndereco().setCidade(c1);
		
		
		Usuario usu2 = new Usuario();
		
		usu2.setNome("CARLA LUA");
		usu2.setCpf("00544141059");
		usu2.setNomeMae("MARGARETA LUA");
		usu2.setNomePai("NALDO LUA");
		usu2.setNomeResponsavel("NALDO LIMA");
		
		LocalDate niver2 =  LocalDate.of(2010, 11, 6);
	     
		usu2.setDataNsacimento(f.parse("15/03/1998"));
		usu2.setTelefone("3382842");
		usu2.setEmail("GATAM@GMAIL.COM");
		usu2.setPcd(StSimNao.NAO);
		usu2.setSt(StAtivo.ATIVO);
		usu2.addPerfil(Perfil.ALUNO);
		usu2.setEndereco(new Endereco());
		usu2.getEndereco().setCep("4150020");
		usu2.getEndereco().setBairro("SUSUARANA");
		usu2.getEndereco().setLogradouro("JAMAICA");
		usu.getEndereco().setNumero("15");
		usu2.getEndereco().setCidade(c1);
		
		enderecoRepository.saveAll(Arrays.asList(usu.getEndereco(), usu2.getEndereco()));
		usuarioRepository.saveAll(Arrays.asList(usu, usu2));
		
		
		Usuario voluntario = new Usuario();
		
		voluntario.setNome("ALLAN DA CRUZ ROSA");
		voluntario.setCpf("01110539517");
		voluntario.setEmail("allancruzrosa@gmail.com");
		voluntario.addPerfil(Perfil.VOLUNTARIO);
		
		
		Usuario voluntario2 = new Usuario();
		
		voluntario2.setNome("ALLAN DA CRUZ ROSA");
		voluntario2.setCpf("01110539517");
		voluntario2.setEmail("allancruzrosa@gmail.com");
		voluntario2.addPerfil(Perfil.VOLUNTARIO);
		
		usuarioRepository.saveAll(Arrays.asList(voluntario, voluntario2));
		
		
		
	}

}
