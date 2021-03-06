package com.allan.spr.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.allan.spr.domain.Atividade;
import com.allan.spr.domain.Cidade;
import com.allan.spr.domain.Endereco;
import com.allan.spr.domain.Estado;
import com.allan.spr.domain.Presenca;
import com.allan.spr.domain.Usuario;
import com.allan.spr.domain.UsuarioPresenca;
import com.allan.spr.domain.enums.CategoriaPresenca;
import com.allan.spr.domain.enums.Perfil;
import com.allan.spr.domain.enums.ProjetoSocial;
import com.allan.spr.domain.enums.StAtivo;
import com.allan.spr.domain.enums.StSimNao;
import com.allan.spr.domain.enums.TipoAtividade;
import com.allan.spr.domain.enums.TipoPresenca;
import com.allan.spr.repositories.AtividadeRepository;
import com.allan.spr.repositories.CidadeRepository;
import com.allan.spr.repositories.EnderecoRepository;
import com.allan.spr.repositories.EstadoRepository;
import com.allan.spr.repositories.PresencaRepository;
import com.allan.spr.repositories.UsuarioPresencaRepository;
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
	private AtividadeRepository atividadeRepository;

	@Autowired
	private UsuarioPresencaRepository usuarioPresencaRepository;
	
	@Autowired
	private PresencaRepository presencaRepository;

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
		
		Usuario adm = new Usuario();
		adm.setNome("Administrator");
		adm.setEmail("administrator@gmail.com");
		String senha = "123";
		adm.setSenha(pe.encode(senha));
		adm.addPerfil(Perfil.ADMIN);

		Usuario usu = new Usuario();

		usu.setNome("IVAN LIMA");
		usu.setCpf("00544141059");
		usu.setNomeMae("BARBARA LIMA");
		usu.setNomePai("JOAQUIM LIMA");
		usu.setNomeResponsavel("BARBARA LIMA");

		// LocalDate niver = LocalDate.of(2010, 11, 6);
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

		LocalDate niver2 = LocalDate.of(2010, 11, 6);

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
		usuarioRepository.saveAll(Arrays.asList(usu, usu2 , adm));

		Usuario voluntario = new Usuario();

		voluntario.setNome("ALLAN DA CRUZ ROSA");
		voluntario.setCpf("01110539517");
		voluntario.setEmail("allancruzrosa@gmail.com");
		voluntario.setTelefone("71992587319");
		voluntario.addPerfil(Perfil.VOLUNTARIO);

		Usuario voluntario2 = new Usuario();

		voluntario2.setNome("ABIDJAN SANTOS ROSA");
		voluntario2.setCpf("14926209047");
		voluntario2.setEmail("abydjan@yahoo.com.br");
		voluntario2.setTelefone("71338205222");
		voluntario2.addPerfil(Perfil.VOLUNTARIO);

		usuarioRepository.saveAll(Arrays.asList(voluntario, voluntario2));

		Atividade atividade = new Atividade();

		atividade.setLicao("Deus é bom !");
		atividade.setTipo(TipoAtividade.AULA);
		atividade.setProjetoSocial(ProjetoSocial.REINTREGAR_KIDS_JAMAICA);
		atividade.setDataCadastro(new Date());

		Atividade atividade2 = new Atividade();

		atividade2.setLicao("Festa de Natal");
		atividade2.setTipo(TipoAtividade.FESTA);
		atividade2.setProjetoSocial(ProjetoSocial.REINTREGAR_KIDS_JAMAICA);
		atividade2.setDataCadastro(new Date());

		Atividade atividade3 = new Atividade();

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date data = formato.parse("09/03/2020");

		atividade3.setLicao("PASSEIO IBAM");
		atividade3.setTipo(TipoAtividade.PASSEIO);
		atividade3.setProjetoSocial(ProjetoSocial.REINTREGAR_KIDS_JAMAICA);
		atividade3.setDataCadastro(data);

		atividadeRepository.saveAll(Arrays.asList(atividade, atividade2, atividade3));

		UsuarioPresenca up = new UsuarioPresenca();
		up.setTipo(TipoPresenca.PRESENTE);
		up.setUsuario(usu);

		UsuarioPresenca up2 = new UsuarioPresenca();
		up2.setTipo(TipoPresenca.PRESENTE);
		up2.setUsuario(usu2);

		usuarioPresencaRepository.saveAll(Arrays.asList(up, up2));
		
		Presenca presenca = new Presenca();
		presenca.setAtividade(atividade);
		presenca.setDataCadastro(new Date());
		presenca.getListUsuarioPresenca().addAll(Arrays.asList(up, up2));
		presenca.setCategoria(CategoriaPresenca.ALUNO);
		presencaRepository.save(presenca);

	}

}
