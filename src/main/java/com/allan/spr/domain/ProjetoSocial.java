package com.allan.spr.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "projeto_social")
@PrimaryKeyJoinColumn(name = "id_projeto_social")
@Inheritance(strategy = InheritanceType.JOINED)
public class ProjetoSocial extends PessoaJuridica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "descricao")
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	

}
