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
import com.allan.spr.domain.Presenca;
import com.allan.spr.domain.Usuario;
import com.allan.spr.domain.UsuarioPresenca;
import com.allan.spr.domain.enums.TipoPresenca;
import com.allan.spr.dto.PresencaDTO;
import com.allan.spr.dto.PresencaNewDTO;
import com.allan.spr.dto.UsuarioPresencaDTO;
import com.allan.spr.dto.UsuarioPresencaNewDTO;
import com.allan.spr.repositories.AtividadeRepository;
import com.allan.spr.repositories.PresencaRepository;
import com.allan.spr.repositories.UsuarioPresencaRepository;
import com.allan.spr.repositories.UsuarioRepository;
import com.allan.spr.services.exceptions.ObjectNotFoundException;

@Service
public class PresencaService {

	@Autowired
	private PresencaRepository repo;

	@Autowired
	private AtividadeRepository repoAtividade;
	
	@Autowired
	private UsuarioPresencaRepository usuarioPresencaRepo;
	
	@Autowired
	private UsuarioRepository repoUsuario;

	public Presenca find(Long id) {

		Optional<Presenca> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Presenca.class.getName()));
	}

	@Transactional
	public Presenca insert(Presenca obj) {

		obj.setId(null);
		preparaInsert(obj);
		usuarioPresencaRepo.saveAll(obj.getListUsuarioPresenca());
		obj = repo.save(obj);

		return obj;
	}
	
	private void preparaInsert(Presenca obj) {
		obj.setDataCadastro(new Date());
	}


	public Presenca update(Presenca obj) {

		Presenca newObj = find(obj.getId());
		updateData(newObj, obj);
		usuarioPresencaRepo.saveAll(newObj.getListUsuarioPresenca());
		return repo.save(newObj);
	}
	
	private void updateData(Presenca newObj, Presenca obj) {

		newObj.setAtividade(obj.getAtividade());
		newObj.setListUsuarioPresenca(obj.getListUsuarioPresenca());
		newObj.setDataCadastro(obj.getDataCadastro());

	}

	public List<Presenca> findAll() {
		return repo.findAll();
	}

	public Page<Presenca> findAll(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest paginacao = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAllPresenca(paginacao);
	}

	public Page<Presenca> findByPresencaDataCadastro(Integer page, Integer linesPerPage, String orderBy,
			String direction, Date data) {

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		String dataFormatada = formato.format(data);

		PageRequest paginacao = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findByPresencaDataCadastro(dataFormatada, paginacao);
	}
	
	private Atividade getAtividade(Long id) {

		Optional<Atividade> obj = repoAtividade.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Atividade.class.getName()));
	}
	
	public Usuario getUsuario(Long id) {

		Optional<Usuario> obj = repoUsuario.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
	}

	public Presenca fromDTO(PresencaNewDTO objDto) {

		Presenca obj = new Presenca();
		obj.setAtividade(getAtividade(objDto.getIdAtividade()));
		obj.setDataCadastro(objDto.getDataCadastro());
		
		for(UsuarioPresencaNewDTO upDto : objDto.getListUsuarioPresenca()) {
			UsuarioPresenca up = new UsuarioPresenca();
			up.setTipo(TipoPresenca.toEnum(upDto.getTipo()));
			up.setUsuario(getUsuario(upDto.getIdUsuario()));
			
			obj.getListUsuarioPresenca().add(up);
		}
		
		return obj;

	}

	public Presenca fromDTO(PresencaDTO objDto) {

		Presenca obj = new Presenca();

		obj.setId(objDto.getId());
		obj.setAtividade(getAtividade(objDto.getIdAtividade()));
		obj.setDataCadastro(objDto.getDataCadastro());
		
		for(UsuarioPresencaDTO upDto : objDto.getListUsuarioPresenca()) {
			UsuarioPresenca up = new UsuarioPresenca();
			up.setId(upDto.getId());
			up.setTipo(TipoPresenca.toEnum(upDto.getTipo()));
			up.setUsuario(getUsuario(upDto.getIdUsuario()));
			
			obj.getListUsuarioPresenca().add(up);
		}
		return obj;
	}

}
