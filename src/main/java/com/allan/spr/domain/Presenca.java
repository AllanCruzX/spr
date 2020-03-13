package com.allan.spr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.allan.spr.domain.enums.CategoriaPresenca;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "presenca")
public class Presenca implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_atividade")
	private Atividade atividade;

	@OneToMany(fetch = FetchType.LAZY)
	private List<UsuarioPresenca> listUsuarioPresenca = new ArrayList<UsuarioPresenca>();

	@JsonFormat(pattern = "dd/MM/yyyy hh:mm")
	@Column(name = "dt_cadastro")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "categoria")
	private CategoriaPresenca categoria;

	public CategoriaPresenca getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaPresenca categoria) {
		this.categoria = categoria;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public List<UsuarioPresenca> getListUsuarioPresenca() {
		return listUsuarioPresenca;
	}

	public void setListUsuarioPresenca(List<UsuarioPresenca> listUsuarioPresenca) {
		this.listUsuarioPresenca = listUsuarioPresenca;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Presenca other = (Presenca) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
