package com.allan.spr.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.allan.spr.domain.Usuario;
import com.allan.spr.domain.enums.Perfil;
import com.allan.spr.domain.enums.ProjetoSocial;
import com.allan.spr.domain.enums.StAtivo;
import com.allan.spr.dto.VoluntarioDTO;
import com.allan.spr.dto.VoluntarioNewDTO;
import com.allan.spr.repositories.UsuarioRepository;
import com.allan.spr.security.UserSS;
import com.allan.spr.services.exceptions.AuthorizationException;
import com.allan.spr.services.exceptions.ObjectNotFoundException;

@Service
public class VoluntarioService {

	@Autowired
	private UsuarioRepository repo;

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
		obj = repo.save(obj);

		return obj;
	}

	private void preparaSave(Usuario obj) {
		setPerfil(obj);
		obj.setProjetoSocial(ProjetoSocial.REINTREGAR_KIDS_JAMAICA);
		obj.setSt(StAtivo.ATIVO);
		obj.setDataCadastro(new Date());
	}

	public Usuario update(Usuario obj) {

		Usuario newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Long id) {

		Usuario newObj = find(id);
		repo.deleteById(newObj.getId());

	}

	private void updateData(Usuario newObj, Usuario obj) {

		newObj.setEmail(obj.getEmail());
		newObj.setNome(obj.getNome());
		newObj.setCpf(obj.getCpf());
		newObj.setTelefone((obj.getTelefone() == null) ? null : obj.getTelefone());

	}

	private void setPerfil(Usuario obj) {
		obj.addPerfil(Perfil.VOLUNTARIO);
	}

	public Usuario findByEmail(String email) {

		Usuario obj = repo.findByEmail(email);

		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id" + obj.getId() + ",Tipo: " + Usuario.class.getName());
		}

		return obj;
	}

	public Page<Usuario> findAllVoluntarios(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest paginacao = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return repo.findAllVoluntarios(Perfil.VOLUNTARIO.getCod(), paginacao);
	}

	public Page<Usuario> findAllVoluntarioNome(Integer page, Integer linesPerPage, String orderBy, String direction,
			String nome) {
		PageRequest paginacao = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAllVoluntarioNome(Perfil.VOLUNTARIO.getCod(), paginacao, "%" + nome + "%");
	}

	public Page<Usuario> findAllVoluntarioCPF(Integer page, Integer linesPerPage, String orderBy, String direction,
			String cpf) {
		PageRequest paginacao = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAllVoluntarioCPF(Perfil.VOLUNTARIO.getCod(), paginacao, "%" + cpf + "%");
	}

	public Page<Usuario> findAllVoluntarioNomeCPF(Integer page, Integer linesPerPage, String orderBy, String direction,
			String nome, String cpf) {
		PageRequest paginacao = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAllVoluntarioNomeCPF(Perfil.VOLUNTARIO.getCod(), paginacao, "%" + nome + "%",  "%" + cpf + "%");
	}

	public Usuario fromDTO(VoluntarioNewDTO objDto) {

		Usuario voluntario = new Usuario();

		voluntario.setNome(objDto.getNome());
		voluntario.setCpf(objDto.getCpf());
		voluntario.setTelefone((objDto.getTelefone() == null) ? null : objDto.getTelefone());
		voluntario.setEmail(objDto.getEmail());
		voluntario.setSenha(objDto.getSenha());

		return voluntario;

	}

	public Usuario fromDTO(VoluntarioDTO objDto) {

		Usuario voluntario = new Usuario();
		voluntario.setId(objDto.getId());
		voluntario.setNome(objDto.getNome());
		voluntario.setCpf(objDto.getCpf());
		voluntario.setTelefone((objDto.getTelefone() == null) ? null : objDto.getTelefone());
		voluntario.setEmail(objDto.getEmail());
		return voluntario;

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

}
