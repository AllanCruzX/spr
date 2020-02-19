package com.allan.spr.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "pessoa_fisica")
@PrimaryKeyJoinColumn(name = "id_pessoa_fisica")
@Inheritance(strategy = InheritanceType.JOINED)
public class PessoaFisica extends Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "cpf")
	private String cpf;

	@Column(name = "nome_mae")
	private String nomeMae;

	@Column(name = "nome_pai")
	private String nomePai;

	@Column(name = "nome_responsavel")
	private String nomeResponsavel;

	@Column(name = "pne")
	private String pne;

	@Column(name = "nascimento")
	private LocalDate dataNsacimento;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public String getPne() {
		return pne;
	}

	public void setPne(String pne) {
		this.pne = pne;
	}

	public LocalDate getDataNsacimento() {
		return dataNsacimento;
	}

	public void setDataNsacimento(LocalDate dataNsacimento) {
		this.dataNsacimento = dataNsacimento;
	}

}
