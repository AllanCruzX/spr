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

import com.allan.spr.domain.Usuario;
import com.allan.spr.dto.UsuarioLogadoDTO;
import com.allan.spr.services.UsuarioLogadoService;

@RestController // Suprime o ResponseBody
@RequestMapping(value = "/usuarios_logado") // para determinar qual é a url
public class UsuarioLogadoResource {
	// @ResponseBody // Dizer para Spring que não vai navegar por uma pagina.( Por
	// padrão, o Spring considera que o retorno do método é o nome da página que ele
	// deve carregar, mas ao utilizar a anotação @ResponseBody, indicamos que o
	// retorno do método deve ser serializado e devolvido no corpo da resposta.)
	// Você percebe que por padrão o Spring pega o retorno e devolve ela em um
	// formato JSON. Na verdade, não é o Spring que faz isso. O Spring usa uma
	// biblioteca chamada Jackson. É o Jackson que faz a conversão de Java para
	// JSON. O Spring usa o Jackson por debaixo dos panos.

	@Autowired
	private UsuarioLogadoService service;

	@GetMapping("/{id}") // @PathVariable atribui o id do @GetMapping ao id do parametro do metodo.o
	@Transactional
	public ResponseEntity<Usuario> find(@PathVariable Long id) {
		Usuario obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@PreAuthorize("hasAnyRole('ADMIN') or hasRole('VOLUNTARIO')")
	@RequestMapping(value = "/email", method = RequestMethod.GET)
	public ResponseEntity<Usuario> find(@RequestParam(value = "email") String email) {
		Usuario obj = service.findByEmail(email);
		return ResponseEntity.ok().body(obj);
	}

	@PreAuthorize("hasAnyRole('ADMIN') or hasRole('VOLUNTARIO')")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<UsuarioLogadoDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page, String nome,
			String nomeResponsavel, @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		// @RequestParam passar parametros no cabeçalho da requisição http , por deful
		// ele deixa o paramentro obrigatorio.
		// Pageable da todo suporte para paginação retorna em que pagina está quantas
		// tem e etc.
		// Pageable paginacao = PageRequest.of(pagina, qtd , Direction.ASC , ordenacao);
		// @PageableDefault Indicar o padrão de paginação/ordenação ao Spring, quando o
		// cliente da API não enviar tais informações

		Page<Usuario> list = service.findAll(page, linesPerPage, orderBy, direction);
		Page<UsuarioLogadoDTO> listDTO = list.map(obj -> new UsuarioLogadoDTO(obj));
		return ResponseEntity.ok().body(listDTO);

	}

}
