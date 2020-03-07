package com.allan.spr.resources;

import java.net.URI;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.allan.spr.domain.Atividade;
import com.allan.spr.dto.AtividadeDTO;
import com.allan.spr.dto.AtividadeNewDTO;
import com.allan.spr.services.AtividadeService;

@RestController 
@RequestMapping(value = "/atividades") 
public class AtividadeResource {
	
	@Autowired
	private AtividadeService service;

	@GetMapping("/{id}") 
	@Transactional
	public ResponseEntity<Atividade> find(@PathVariable Long id) {
		Atividade obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody AtividadeNewDTO objDto) {

		Atividade obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> insert(@Valid @RequestBody AtividadeNewDTO objDto, @PathVariable Long id) {
		Atividade obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}


	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Atividade> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<AtividadeDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page, Integer tipo, Date inicio,Date fim,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		Page<AtividadeDTO> listDTO = null;

		if (tipo == null  && inicio == null && fim == null) {
			Page<Atividade> list = service.findAllAtividades(page, linesPerPage, orderBy, direction);
			listDTO = list.map(obj -> new AtividadeDTO(obj));
		}

		if (tipo != null && inicio == null && fim == null) {
			Page<Atividade> list = service.findByAtividadesTipoAtividade(page, linesPerPage, orderBy, direction, tipo);
			listDTO = list.map(obj -> new AtividadeDTO(obj));
		}

		if (tipo == null && inicio != null && fim != null) {
			Page<Atividade> list = service.findByAtividadesDataCadastro(page, linesPerPage, orderBy, direction, inicio , fim);
			listDTO = list.map(obj -> new AtividadeDTO(obj));
		}

		if (tipo != null && inicio != null && fim != null) {
			Page<Atividade> list = service.findByAtividadesTipoAtividadeDataCadastro(page, linesPerPage, orderBy, direction, tipo, inicio, fim);
			listDTO = list.map(obj -> new AtividadeDTO(obj));
		}

		return ResponseEntity.ok().body(listDTO);

	}

}
