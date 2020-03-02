package com.allan.spr.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.allan.spr.domain.enums.StSimNao;
import com.fasterxml.jackson.annotation.JsonFormat;

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

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "pcd")
	private StSimNao pcd;// pessoa com deficiencia

	@JsonFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name = "nascimento")
	private Date dataNsacimento;

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

	public StSimNao getPcd() {
		return pcd;
	}

	public void setPcd(StSimNao pcd) {
		this.pcd = pcd;
	}

	public Date getDataNsacimento() {
		return dataNsacimento;
	}

	public void setDataNsacimento(Date dataNsacimento) {
		this.dataNsacimento = dataNsacimento;
	}

}
