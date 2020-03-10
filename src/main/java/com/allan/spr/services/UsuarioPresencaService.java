package com.allan.spr.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.allan.spr.domain.Atividade;
import com.allan.spr.domain.Usuario;
import com.allan.spr.domain.UsuarioPresenca;
import com.allan.spr.domain.enums.TipoPresenca;
import com.allan.spr.dto.UsuarioPresencaDTO;
import com.allan.spr.dto.UsuarioPresencaNewDTO;
import com.allan.spr.repositories.AtividadeRepository;
import com.allan.spr.repositories.UsuarioPresencaRepository;
import com.allan.spr.repositories.UsuarioRepository;
import com.allan.spr.services.exceptions.ObjectNotFoundException;

@Service
public class UsuarioPresencaService {

	@Autowired
	private UsuarioPresencaRepository repo;

	@Autowired
	private UsuarioRepository repoUsuario;

	@Autowired
	private AtividadeRepository repoAtividade;

	public UsuarioPresenca find(Long id) {

		Optional<UsuarioPresenca> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + UsuarioPresenca.class.getName()));
	}

	@Transactional
	public UsuarioPresenca insert(UsuarioPresenca obj) {

		obj.setId(null);
		preparaSave(obj);
		obj = repo.save(obj);

		return obj;
	}

	private void preparaSave(UsuarioPresenca obj) {
		obj.setDataCadastro(new Date());
	}

	public UsuarioPresenca update(UsuarioPresenca obj) {

		UsuarioPresenca newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public Usuario getUsuario(Long id) {

		Optional<Usuario> obj = repoUsuario.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
	}

	public Atividade getAtividade(Long id) {

		Optional<Atividade> obj = repoAtividade.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
	}

	private void updateData(UsuarioPresenca newObj, UsuarioPresenca obj) {

		newObj.setUsuario(getUsuario(obj.getUsuario().getId()));
		newObj.setAtividade(getAtividade(obj.getAtividade().getId()));
		newObj.setTipo(obj.getTipo());

	}

	public List<UsuarioPresenca> findAll() {
		return repo.findAll();
	}

	public Page<UsuarioPresenca> findAll(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest paginacao = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(paginacao);
	}

	public Page<UsuarioPresenca> findByUsuarioPresencaDataCadastro(Integer page, Integer linesPerPage, String orderBy,
			String direction, Date data) {

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		String dataFormatada = formato.format(data);

		PageRequest paginacao = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findByUsuarioPresencaDataCadastro(dataFormatada, paginacao);
	}

	public Page<UsuarioPresenca> findByUsuarioPresencaFromUsuario(Integer page, Integer linesPerPage, String orderBy,
			String direction, Long idUsuario) {
		PageRequest paginacao = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findByUsuarioPresencaFromUsuario(idUsuario, paginacao);
	}

	public UsuarioPresenca fromDTO(UsuarioPresencaNewDTO objDto) {

		UsuarioPresenca obj = new UsuarioPresenca();

		obj.setUsuario(getUsuario(objDto.getIdUsuario()));
		obj.setAtividade(getAtividade(objDto.getIdAtividade()));
		obj.setTipo(TipoPresenca.toEnum(objDto.getTipo()));
		obj.setDataCadastro(objDto.getDataCadastro());
		return obj;

	}

	public UsuarioPresenca fromDTO(UsuarioPresencaDTO objDto) {

		UsuarioPresenca obj = new UsuarioPresenca();

		obj.setId(objDto.getId());
		obj.setUsuario(getUsuario(objDto.getIdUsuario()));
		obj.setAtividade(getAtividade(objDto.getIdAtividade()));
		obj.setTipo(TipoPresenca.toEnum(objDto.getTipo()));
		obj.setDataCadastro(objDto.getDataCadastro());
		return obj;

	}

}
