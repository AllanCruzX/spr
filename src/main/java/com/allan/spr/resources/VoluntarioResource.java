package com.allan.spr.resources;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.allan.spr.domain.Usuario;
import com.allan.spr.dto.VoluntarioDTO;
import com.allan.spr.dto.VoluntarioNewDTO;
import com.allan.spr.services.VoluntarioService;

@RestController // Suprime o ResponseBody
@RequestMapping(value = "/voluntarios") // para determinar qual é a url
public class VoluntarioResource {
	// @ResponseBody // Dizer para Spring que não vai navegar por uma pagina.( Por
	// padrão, o Spring considera que o retorno do método é o nome da página que ele
	// deve carregar, mas ao utilizar a anotação @ResponseBody, indicamos que o
	// retorno do método deve ser serializado e devolvido no corpo da resposta.)
	// Você percebe que por padrão o Spring pega o retorno e devolve ela em um
	// formato JSON. Na verdade, não é o Spring que faz isso. O Spring usa uma
	// biblioteca chamada Jackson. É o Jackson que faz a conversão de Java para
	// JSON. O Spring usa o Jackson por debaixo dos panos.

	@Autowired
	private VoluntarioService service;

	@GetMapping("/{id}") // @PathVariable atribui o id do @GetMapping ao id do parametro do metodo.o
	@Transactional
	public ResponseEntity<Usuario> find(@PathVariable Long id) {
		Usuario obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody VoluntarioNewDTO objDto) {

		Usuario obj = service.fromDTO(objDto);

		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();

		// Requisição http com codigo 201 um novo recurso foi criado com sucesso no
		// servidor.
		// ResponseEntity tipo de objeto que vou devolver no corpo da resposta http.
		// Isso acontece porque toda vez que devolvo 201 para o cliente, além de
		// devolver o código, tenho que devolver mais duas coisas. Uma delas é um
		// cabeçalho http, chamado lo
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> insert(@Valid @RequestBody VoluntarioDTO objDto, @PathVariable Long id) {
		Usuario obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/email", method = RequestMethod.GET)
	public ResponseEntity<Usuario> find(@RequestParam(value = "value") String email) {
		Usuario obj = service.findByEmail(email);
		return ResponseEntity.ok().body(obj);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Usuario> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	/*
	 * @RequestMapping(method = RequestMethod.GET) public
	 * ResponseEntity<List<VoluntarioDTO>> findAll() {
	 * 
	 * List<Usuario> list = service.findAll(); List<VoluntarioDTO> listDTO =
	 * list.stream().map(obj -> new
	 * VoluntarioDTO(obj)).collect(Collectors.toList()); return
	 * ResponseEntity.ok().body(listDTO);
	 * 
	 * }
	 */

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<VoluntarioDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page, String nome, String cpf,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		// @RequestParam passar parametros no cabeçalho da requisição http , por deful
		// ele deixa o paramentro obrigatorio.
		// Pageable da todo suporte para paginação retorna em que pagina está quantas
		// tem e etc.
		// Pageable paginacao = PageRequest.of(pagina, qtd , Direction.ASC , ordenacao);
		// @PageableDefault Indicar o padrão de paginação/ordenação ao Spring, quando o
		// cliente da API não enviar tais informações

		Page<VoluntarioDTO> listDTO = null;

		if (nome == null && cpf == null) {
			Page<Usuario> list = service.findAllVoluntarios(page, linesPerPage, orderBy, direction);
			listDTO = list.map(obj -> new VoluntarioDTO(obj));
		}

		if (nome != null && cpf == null) {
			Page<Usuario> list = service.findAllVoluntarioNome(page, linesPerPage, orderBy, direction, nome);
			listDTO = list.map(obj -> new VoluntarioDTO(obj));
		}

		if (nome == null && cpf != null) {
			Page<Usuario> list = service.findAllVoluntarioCPF(page, linesPerPage, orderBy, direction, cpf);
			listDTO = list.map(obj -> new VoluntarioDTO(obj));
		}

		if (nome != null && cpf != null) {
			Page<Usuario> list = service.findAllVoluntarioNomeCPF(page, linesPerPage, orderBy, direction, nome, cpf);
			listDTO = list.map(obj -> new VoluntarioDTO(obj));
		}

		return ResponseEntity.ok().body(listDTO);

	}

}
