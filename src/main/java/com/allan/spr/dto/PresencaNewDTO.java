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

public class PresencaNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull(message = "Preenchimento obrigatório")
	private Long idAtividade;

	@JsonFormat(pattern = "dd/MM/yyyy hh:mm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;
	
	private int categoria;

	@NotEmpty(message = "Preenchimento obrigatório")
	private List<UsuarioPresencaNewDTO> listUsuarioPresenca = new ArrayList<UsuarioPresencaNewDTO>();
	
	

	public PresencaNewDTO() {

	}

	public PresencaNewDTO(Presenca presenca) {

		idAtividade = presenca.getAtividade().getId();
		dataCadastro = presenca.getDataCadastro();
		categoria = presenca.getCategoria().getCod();

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

	public List<UsuarioPresencaNewDTO> getListUsuarioPresenca() {
		return listUsuarioPresenca;
	}

	public void setListUsuarioPresenca(List<UsuarioPresencaNewDTO> listUsuarioPresenca) {
		this.listUsuarioPresenca = listUsuarioPresenca;
	}

	public int getCategoria() {
		return categoria;
	}

	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}

}
