package com.allan.spr.resources;

import java.net.URI;
import java.util.Date;
import java.util.List;

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

import com.allan.spr.domain.UsuarioPresenca;
import com.allan.spr.dto.UsuarioPresencaDTO;
import com.allan.spr.dto.UsuarioPresencaNewDTO;
import com.allan.spr.services.UsuarioPresencaService;

@RestController
@RequestMapping(value = "/presencas")
public class UsuarioPresencaResource {

	@Autowired
	private UsuarioPresencaService service;

	@GetMapping("/{id}")
	@Transactional
	public ResponseEntity<UsuarioPresenca> find(@PathVariable Long id) {
		UsuarioPresenca obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody UsuarioPresencaNewDTO objDto) {

		UsuarioPresenca obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();

	}
	
	

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> insert(@Valid @RequestBody UsuarioPresencaNewDTO objDto, @PathVariable Long id) {
		UsuarioPresenca obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<UsuarioPresencaDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page, Date data,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		Page<UsuarioPresencaDTO> listDTO = null;

		if (data == null) {
			Page<UsuarioPresenca> list = service.findAll(page, linesPerPage, orderBy, direction);
			listDTO = list.map(obj -> new UsuarioPresencaDTO(obj));
		} else {
			Page<UsuarioPresenca> list = service.findByUsuarioPresencaDataCadastro(page, linesPerPage, orderBy,
					direction, data);
			listDTO = list.map(obj -> new UsuarioPresencaDTO(obj));
		}

		return ResponseEntity.ok().body(listDTO);

	}

	@RequestMapping(value = "/page/presenca", method = RequestMethod.GET)
	public ResponseEntity<Page<UsuarioPresencaDTO>> findByUsuarioPresencaFromUsuario(
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page, Long idUsuario,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		Page<UsuarioPresenca> list = service.findByUsuarioPresencaFromUsuario(page, linesPerPage, orderBy, direction,
				idUsuario);
		Page<UsuarioPresencaDTO> listDTO = list.map(obj -> new UsuarioPresencaDTO(obj));
		return ResponseEntity.ok().body(listDTO);

	}

}
