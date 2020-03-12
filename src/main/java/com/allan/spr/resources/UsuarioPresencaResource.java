package com.allan.spr.resources;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.allan.spr.domain.UsuarioPresenca;
import com.allan.spr.dto.UsuarioPresencaDTO;
import com.allan.spr.services.UsuarioPresencaService;

@RestController
@RequestMapping(value = "/usuarios_presencas")
public class UsuarioPresencaResource {

	@Autowired
	private UsuarioPresencaService service;

	@GetMapping("/{id}")
	@Transactional
	public ResponseEntity<UsuarioPresenca> find(@PathVariable Long id) {
		UsuarioPresenca obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}


	@PreAuthorize("hasAnyRole('ADMIN')")
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
