package com.allan.spr.services;

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
import com.allan.spr.domain.enums.ProjetoSocial;
import com.allan.spr.domain.enums.TipoAtividade;
import com.allan.spr.dto.AtividadeDTO;
import com.allan.spr.dto.AtividadeNewDTO;
import com.allan.spr.repositories.AtividadeRepository;
import com.allan.spr.services.exceptions.ObjectNotFoundException;

@Service
public class AtividadeService {

	@Autowired
	private AtividadeRepository repo;

	public Atividade find(Long id) {

		Optional<Atividade> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Atividade.class.getName()));
	}

	@Transactional
	public Atividade insert(Atividade obj) {

		obj.setId(null);
		preparaSave(obj);
		obj = repo.save(obj);

		return obj;
	}

	private void preparaSave(Atividade obj) {
		obj.setProjetoSocial(ProjetoSocial.REINTREGAR_KIDS_JAMAICA);
		obj.setDataCadastro(new Date());
	}

	public Atividade update(Atividade obj) {

		Atividade newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Long id) {

		Atividade newObj = find(id);
		repo.deleteById(newObj.getId());

	}

	private void updateData(Atividade newObj, Atividade obj) {

		newObj.setLicao(obj.getLicao());
		newObj.setProjetoSocial(obj.getProjetoSocial());
		newObj.setTipo(obj.getTipo());

	}
	
	public List<Atividade> findAll(){
		return repo.findAll();
	}

	public Page<Atividade> findAllAtividades(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest paginacao = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(paginacao);
	}

	public Page<Atividade> findByAtividadesTipoAtividade(Integer page, Integer linesPerPage, String orderBy,
			String direction, int tipo) {
		PageRequest paginacao = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findByAtividadesTipoAtividade(TipoAtividade.toEnum(tipo), paginacao);
	}

	public Page<Atividade> findByAtividadesDataCadastro(Integer page, Integer linesPerPage, String orderBy,
			String direction, Date inicio, Date fim) {
		fim.setDate(fim.getDate() + 1);
		PageRequest paginacao = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findByAtividadesDataCadastro(inicio, fim, paginacao);
	}

	public Page<Atividade> findByAtividadesTipoAtividadeDataCadastro(Integer page, Integer linesPerPage, String orderBy,
			String direction, int tipo, Date inicio, Date fim) {
		fim.setDate(fim.getDate() + 1);
		PageRequest paginacao = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findByAtividadesTipoAtividadeDataCadastro(TipoAtividade.toEnum(tipo), inicio, fim, paginacao);
	}

	public Atividade fromDTO(AtividadeNewDTO objDto) {

		Atividade obj = new Atividade();

		obj.setLicao(objDto.getLicao());
		obj.setTipo(TipoAtividade.toEnum(objDto.getTipo()));
		obj.setProjetoSocial(ProjetoSocial.toEnum(objDto.getProjetoSocial()));

		return obj;

	}

	public Atividade fromDTO(AtividadeDTO objDto) {

		Atividade obj = new Atividade();

		obj.setId(objDto.getId());
		obj.setLicao(objDto.getLicao());
		obj.setTipo(TipoAtividade.toEnum(objDto.getTipo()));
		obj.setProjetoSocial(ProjetoSocial.toEnum(objDto.getProjetoSocial()));

		return obj;

	}

}
