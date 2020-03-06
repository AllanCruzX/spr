package com.allan.spr.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.allan.spr.domain.Atividade;


public class AtividadeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String licao;
	
	@NotNull(message = "Preenchimento obrigatório")
	private int tipo;
	
	@NotNull(message = "Preenchimento obrigatório")
	private int projetoSocial;
	

	public AtividadeDTO() {

	}

	public AtividadeDTO(Atividade atividade) {
		
		id = atividade.getId();
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
