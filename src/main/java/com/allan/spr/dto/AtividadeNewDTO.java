package com.allan.spr.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.allan.spr.domain.Atividade;
import com.fasterxml.jackson.annotation.JsonFormat;


public class AtividadeNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String licao;
	
	@NotNull(message = "Preenchimento obrigatório")
	private int tipo;
	
	@NotNull(message = "Preenchimento obrigatório")
	private int projetoSocial;
	
	@JsonFormat(pattern = "dd/MM/yyyy hh:mm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;
	
	

	public AtividadeNewDTO() {

	}

	public AtividadeNewDTO(Atividade atividade) {

		licao = atividade.getLicao();
		tipo = atividade.getTipo().getCod();
		projetoSocial = atividade.getProjetoSocial().getCod();
		dataCadastro = atividade.getDataCadastro();

	}

	public String getLicao() {
		return licao;
	}

	public void setLicao(String licao) {
		this.licao = licao;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getProjetoSocial() {
		return projetoSocial;
	}

	public void setProjetoSocial(int projetoSocial) {
		this.projetoSocial = projetoSocial;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	

}
