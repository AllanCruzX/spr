package com.allan.spr.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.allan.spr.domain.UsuarioPresenca;
import com.fasterxml.jackson.annotation.JsonFormat;

public class UsuarioPresencaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotEmpty(message = "Preenchimento obrigatório")
	private Long idUsuario;

	@NotEmpty(message = "Preenchimento obrigatório")
	private Long idAtividade;

	@NotNull(message = "Preenchimento obrigatório")
	private int tipo;
	
	@JsonFormat(pattern = "dd/MM/yyyy hh:mm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;

	public UsuarioPresencaDTO() {

	}

	public UsuarioPresencaDTO(UsuarioPresenca usuarioPresenca) {

		id = usuarioPresenca.getId();
		tipo = usuarioPresenca.getTipo().getCod();
		idUsuario = usuarioPresenca.getUsuario().getId();
		idAtividade = usuarioPresenca.getAtividade().getId();
		dataCadastro = usuarioPresenca.getDataCadastro();

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

	public Long getIdAtividade() {
		return idAtividade;
	}

	public void setIdAtividade(Long idAtividade) {
		this.idAtividade = idAtividade;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	

}
