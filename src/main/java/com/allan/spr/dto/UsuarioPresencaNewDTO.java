package com.allan.spr.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.allan.spr.domain.UsuarioPresenca;

public class UsuarioPresencaNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;


	@NotEmpty(message = "Preenchimento obrigatório")
	private Long idUsuario;

	@NotNull(message = "Preenchimento obrigatório")
	private int tipo;

	public UsuarioPresencaNewDTO() {

	}

	public UsuarioPresencaNewDTO(UsuarioPresenca usuarioPresenca) {

		tipo = usuarioPresenca.getTipo().getCod();
		idUsuario = usuarioPresenca.getUsuario().getId();

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
