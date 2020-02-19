package com.allan.spr.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.allan.spr.domain.enums.TipoAtividade;


@Entity
@Table(name = "atividade")
public class Atividade implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "licao")
	private String licao;
	
	@Column(name = "dt_criacao")
	private LocalDate dataCriacao;
	
	@ManyToOne
	@JoinColumn(name="proj_social_id")
	private ProjetoSocial projetoSocial;
	
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "tipo")
	private TipoAtividade tipo;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="lista_presenca_id")
	private ListaPresenca listaPresenca;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLicao() {
		return licao;
	}

	public void setLicao(String licao) {
		this.licao = licao;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public ProjetoSocial getProjetoSocial() {
		return projetoSocial;
	}

	public void setProjetoSocial(ProjetoSocial projetoSocial) {
		this.projetoSocial = projetoSocial;
	}

	public TipoAtividade getTipo() {
		return tipo;
	}

	public void setTipo(TipoAtividade tipo) {
		this.tipo = tipo;
	}

	public ListaPresenca getListaPresenca() {
		return listaPresenca;
	}

	public void setListaPresenca(ListaPresenca listaPresenca) {
		this.listaPresenca = listaPresenca;
	}
	

}
