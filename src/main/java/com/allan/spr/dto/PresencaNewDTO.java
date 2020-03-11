package com.allan.spr.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import com.allan.spr.domain.Presenca;
import com.allan.spr.domain.UsuarioPresenca;
import com.fasterxml.jackson.annotation.JsonFormat;

public class PresencaNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
	private Long idAtividade;

	@JsonFormat(pattern = "dd/MM/yyyy hh:mm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;

	@NotEmpty(message = "Preenchimento obrigatório")
	private Set<UsuarioPresencaNewDTO> listUsuarioPresenca = new HashSet<UsuarioPresencaNewDTO>();

	public PresencaNewDTO() {

	}

	public PresencaNewDTO(Presenca presenca) {

		idAtividade = presenca.getAtividade().getId();
		dataCadastro = presenca.getDataCadastro();

		for (UsuarioPresenca usuPresenca : presenca.getListUsuarioPresenca()) {
			UsuarioPresencaNewDTO obj = new UsuarioPresencaNewDTO();
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

	public Set<UsuarioPresencaNewDTO> getListUsuarioPresenca() {
		return listUsuarioPresenca;
	}

	public void setListUsuarioPresenca(Set<UsuarioPresencaNewDTO> listUsuarioPresenca) {
		this.listUsuarioPresenca = listUsuarioPresenca;
	}

}
