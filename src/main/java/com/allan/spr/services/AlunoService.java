package com.allan.spr.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.allan.spr.domain.Cidade;
import com.allan.spr.domain.Endereco;
import com.allan.spr.domain.Usuario;
import com.allan.spr.domain.enums.Perfil;
import com.allan.spr.domain.enums.StAtivo;
import com.allan.spr.domain.enums.StSimNao;
import com.allan.spr.dto.AlunoDTO;
import com.allan.spr.dto.AlunoNewDTO;
import com.allan.spr.repositories.CidadeRepository;
import com.allan.spr.repositories.EnderecoRepository;
import com.allan.spr.repositories.UsuarioRepository;
import com.allan.spr.security.UserSS;
import com.allan.spr.services.exceptions.AuthorizationException;
import com.allan.spr.services.exceptions.DataIntegrityException;
import com.allan.spr.services.exceptions.ObjectNotFoundException;

@Service
public class AlunoService {

	@Autowired
	private UsuarioRepository repo;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private S3Service s3service;

	@Autowired
	private ImageService imageService;

	@Value("${img.prefix.client.profile}")
	private String prefix;

	@Value("${img.profile.size}")
	private Integer size;

	public Usuario find(Long id) {
		
		Optional<Usuario> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
	}

	@Transactional
	public Usuario insert(Usuario obj) {
		
			
		obj.setId(null);
		preparaSave(obj);
		enderecoRepository.save(obj.getEndereco());
		obj = repo.save(obj);
		
		return obj;
	}
	
	private void preparaSave(Usuario obj) {
		setPerfil(obj);
		obj.setSt(StAtivo.ATIVO);
		obj.setDataCadastro(new Date());
	}

	public Usuario update(Usuario obj) {
						
		Usuario newObj = find(obj.getId());
		updateData(newObj, obj);
		enderecoRepository.save(newObj.getEndereco());
		return repo.save(newObj);
	}
	
	public void delete(Long id) {
		
			
		Usuario newObj = find(id);
		try {
			repo.deleteById(newObj.getId());
			enderecoRepository.deleteById(newObj.getEndereco().getId());
			

		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há enderecos relacionados");
		}

	}

	private void updateData(Usuario newObj, Usuario obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		
		
		newObj.setNome(obj.getNome());
		newObj.setNomeMae((obj.getNomeMae() == null) ? null : obj.getNomeMae());
		newObj.setNomePai((obj.getNomePai() == null) ? null : obj.getNomePai());
		newObj.setNomeResponsavel(obj.getNomeResponsavel());
		newObj.setCpf((obj.getCpf() == null) ? null : obj.getCpf());
		newObj.setTelefone((obj.getTelefone() == null) ? null : obj.getTelefone());
		newObj.setEmail((obj.getEmail() == null) ? null : obj.getEmail());
		newObj.setPcd(obj.getPcd());
		
		newObj.getEndereco().setCep((obj.getEndereco().getCep() == null) ? null : obj.getEndereco().getCep() );
		newObj.getEndereco().setBairro((obj.getEndereco().getBairro() == null) ? null : obj.getEndereco().getBairro());
		newObj.getEndereco().setLogradouro((obj.getEndereco().getLogradouro() == null) ? null : obj.getEndereco().getLogradouro());
		newObj.getEndereco().setNumero((obj.getEndereco().getNumero() == null) ? null : obj.getEndereco().getNumero());

		if (obj.getEndereco().getCidade() != null && obj.getEndereco().getCidade().getId() != null) {
			Cidade cid = findByIdCidade(obj.getEndereco().getCidade().getId());
			newObj.getEndereco().setCidade(cid);
		}

	}
	
	private void setPerfil(Usuario obj) {
		obj.addPerfil(Perfil.ALUNO);
	}

	public Usuario findByEmail(String email) {

		UserSS user = UserService.authenticated();

		if (user == null || !user.hesRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}

		Usuario obj = repo.findByEmail(email);

		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id" + user.getId() + ",Tipo: " + Usuario.class.getName());
		}

		return obj;
	}

	
	public Page<Usuario> findAllAlunos(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest paginacao = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
			
		return repo.findAllAlunos(Perfil.ALUNO.getCod(), paginacao);
	}
	
	public Page<Usuario> findByAlunosNome(Integer page, Integer linesPerPage, String orderBy, String direction, String nome ){
		PageRequest paginacao = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAllAlunosNome(Perfil.ALUNO.getCod(), paginacao , "%" + nome + "%" );
	}
	
	public Page<Usuario> findByAlunosNomeResponsavel(Integer page, Integer linesPerPage, String orderBy, String direction, String nomeResponsavel ){
		PageRequest paginacao = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAllAlunosNomeResponsavel(Perfil.ALUNO.getCod(), paginacao ,"%" + nomeResponsavel + "%"  );
	}
	
	public Page<Usuario> findByAlunosNomeNomeResponsavel(Integer page, Integer linesPerPage, String orderBy, String direction, String nome ,String nomeResponsavel){
		PageRequest paginacao = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAllAlunosNomeNomeResponsavel(Perfil.ALUNO.getCod(), paginacao , "%" + nome + "%" , "%" +nomeResponsavel + "%" );
	}
	


	public Usuario fromDTO(AlunoNewDTO objDto) {

		Usuario aluno = new Usuario();
		Endereco endereco = new Endereco();

		aluno.setNome(objDto.getNome());
		aluno.setNomeMae((objDto.getNomeMae() == null) ? null : objDto.getNomeMae());
		aluno.setNomePai((objDto.getNomePai() == null) ? null : objDto.getNomePai());
		aluno.setNomeResponsavel(objDto.getNomeResponsavel());
		aluno.setCpf((objDto.getCpf() == null) ? null : objDto.getCpf());
		aluno.setTelefone((objDto.getTelefone() == null) ? null : objDto.getTelefone());
		aluno.setEmail((objDto.getEmail() == null) ? null : objDto.getEmail());
		aluno.setPcd(StSimNao.toEnum(objDto.getPcd()));
		aluno.setDataNsacimento((objDto.getDataNsacimento() == null) ? null : objDto.getDataNsacimento());

		endereco.setCep((objDto.getCep() == null) ? null : objDto.getCep());
		endereco.setBairro((objDto.getBairro() == null) ? null : objDto.getBairro());
		endereco.setLogradouro((objDto.getLogradouro() == null) ? null : objDto.getLogradouro());
		endereco.setNumero((objDto.getNumero() == null) ? null : objDto.getNumero());

		if (objDto.getCidadeId() != null) {
			Cidade cid = findByIdCidade(objDto.getCidadeId());
			endereco.setCidade(cid);
		}
		
		aluno.setEndereco(endereco);
		
		return aluno;

	}
	
	public Usuario fromDTO(AlunoDTO objDto) {

		Usuario aluno = new Usuario();
		Endereco endereco = new Endereco();
		aluno.setId(objDto.getId());
		aluno.setNome(objDto.getNome());
		aluno.setNomeMae((objDto.getNomeMae() == null) ? null : objDto.getNomeMae());
		aluno.setNomePai((objDto.getNomePai() == null) ? null : objDto.getNomePai());
		aluno.setNomeResponsavel(objDto.getNomeResponsavel());
		aluno.setCpf((objDto.getCpf() == null) ? null : objDto.getCpf());
		aluno.setDataNsacimento((objDto.getDataNsacimento() == null) ? null : objDto.getDataNsacimento());
		aluno.setTelefone((objDto.getTelefone() == null) ? null : objDto.getTelefone());
		aluno.setEmail((objDto.getEmail() == null) ? null : objDto.getEmail());
		aluno.setPcd(StSimNao.toEnum(objDto.getPcd()));
		
	
			
			endereco.setId(objDto.getEnderecoId());
			endereco.setCep((objDto.getCep() == null) ? null : objDto.getCep());
			endereco.setBairro((objDto.getBairro() == null) ? null : objDto.getBairro());
			endereco.setLogradouro((objDto.getLogradouro() == null) ? null : objDto.getLogradouro());
			endereco.setNumero((objDto.getNumero() == null) ? null : objDto.getNumero());

			if (objDto.getCidadeId() != null) {
				Cidade cid = findByIdCidade(objDto.getCidadeId());
				endereco.setCidade(cid);
			}
			
		
	
		
		aluno.setEndereco(endereco);
		
		return aluno;

	}

	private Cidade findByIdCidade(Long id) {
		Optional<Cidade> obj = cidadeRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cidade.class.getName()));
	}

	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);

		String fileName = prefix + user.getId() + ".jpg";

		return s3service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}
	
	public List<Usuario> findAll() {
					
		return repo.findAll();
	}
	
	

}
