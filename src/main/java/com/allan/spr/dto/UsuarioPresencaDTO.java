package com.allan.spr.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.allan.spr.domain.UsuarioPresenca;

public class UsuarioPresencaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotEmpty(message = "Preenchimento obrigatório")
	private Long idUsuario;

	@NotNull(message = "Preenchimento obrigatório")
	private int tipo;

	public UsuarioPresencaDTO() {

	}

	public UsuarioPresencaDTO(UsuarioPresenca usuarioPresenca) {

		id = usuarioPresenca.getId();
		tipo = usuarioPresenca.getTipo().getCod();
		idUsuario = usuarioPresenca.getUsuario().getId();
		
	

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

}
