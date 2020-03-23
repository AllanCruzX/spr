package com.allan.spr.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.allan.spr.domain.Usuario;
import com.allan.spr.dto.UsuarioLogadoDTO;
import com.allan.spr.repositories.UsuarioRepository;
import com.allan.spr.services.exceptions.ObjectNotFoundException;

@Service
public class UsuarioLogadoService {

	@Autowired
	private UsuarioRepository repo;

	@Autowired
	private S3Service s3service;

	@Value("${img.prefix.client.profile}")
	private String prefix;

	@Value("${img.profile.size}")
	private Integer size;

	public Usuario find(Long id) {

		Optional<Usuario> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
	}

	public Usuario findByEmail(String email) {

		Usuario obj = repo.findByEmail(email);

		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id" + obj.getId() + ",Tipo: " + Usuario.class.getName());
		}

		return obj;
	}

	public Page<Usuario> findAll(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest paginacao = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return repo.findAll(paginacao);
	}

	public Usuario fromDTO(UsuarioLogadoDTO objDto) {

		Usuario usu = new Usuario();

		usu.setId(objDto.getId());
		usu.setNome(objDto.getNome());
		usu.setEmail(objDto.getEmail());

		return usu;

	}

}
