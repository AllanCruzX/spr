package com.allan.spr.resources;

import java.net.URI;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.allan.spr.domain.Presenca;
import com.allan.spr.dto.PresencaDTO;
import com.allan.spr.dto.PresencaNewDTO;
import com.allan.spr.services.PresencaService;

@RestController
@RequestMapping(value = "/presencas")
public class PresencaResource {

	@Autowired
	private PresencaService service;

	@GetMapping("/{id}")
	@Transactional
	public ResponseEntity<Presenca> find(@PathVariable Long id) {
		Presenca obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody PresencaNewDTO objDto) {

		Presenca obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> insert(@Valid @RequestBody PresencaNewDTO objDto, @PathVariable Long id) {
		Presenca obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<PresencaDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page, Date data,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		Page<PresencaDTO> listDTO = null;

		if (data == null) {
			Page<Presenca> list = service.findAll(page, linesPerPage, orderBy, direction);
			listDTO = list.map(obj -> new PresencaDTO(obj));
		} else {
			Page<Presenca> list = service.findByPresencaDataCadastro(page, linesPerPage, orderBy, direction, data);
			listDTO = list.map(obj -> new PresencaDTO(obj));
		}

		return ResponseEntity.ok().body(listDTO);

	}

}
