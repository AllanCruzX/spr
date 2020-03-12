package com.allan.spr.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.allan.spr.domain.Presenca;
import com.allan.spr.domain.UsuarioPresenca;
import com.fasterxml.jackson.annotation.JsonFormat;

public class PresencaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotNull(message = "Preenchimento obrigatório")
	private Long idAtividade;

	@JsonFormat(pattern = "dd/MM/yyyy hh:mm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;

	@NotEmpty(message = "Preenchimento obrigatório")
	private List<UsuarioPresencaDTO> listUsuarioPresenca = new ArrayList<UsuarioPresencaDTO>();

	public PresencaDTO() {

	}

	public PresencaDTO(Presenca presenca) {
		id = presenca.getId();
		idAtividade = presenca.getAtividade().getId();
		dataCadastro = presenca.getDataCadastro();

		for (UsuarioPresenca usuPresenca : presenca.getListUsuarioPresenca()) {
			UsuarioPresencaDTO obj = new UsuarioPresencaDTO();
			obj.setId(usuPresenca.getId());
			obj.setIdUsuario(usuPresenca.getUsuario().getId());
			obj.setTipo(usuPresenca.getTipo().getCod());
			listUsuarioPresenca.add(obj);
		}

	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Long getIdAtividade() {
		return idAtividade;
	}

	public void setIdAtividade(Long idAtividade) {
		this.idAtividade = idAtividade;
	}

	public List<UsuarioPresencaDTO> getListUsuarioPresenca() {
		return listUsuarioPresenca;
	}

	public void setListUsuarioPresenca(List<UsuarioPresencaDTO> listUsuarioPresenca) {
		this.listUsuarioPresenca = listUsuarioPresenca;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
