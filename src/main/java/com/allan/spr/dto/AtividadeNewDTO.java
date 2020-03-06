package com.allan.spr.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.allan.spr.domain.Atividade;


public class AtividadeNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String licao;
	
	@NotNull(message = "Preenchimento obrigatório")
	private int tipo;
	
	@NotNull(message = "Preenchimento obrigatório")
	private int projetoSocial;
	

	public AtividadeNewDTO() {

	}

	public AtividadeNewDTO(Atividade atividade) {

		licao = atividade.getLicao();
		tipo = atividade.getTipo().getCod();
		projetoSocial = atividade.getProjetoSocial().getCod();

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


	

}
