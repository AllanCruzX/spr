package com.allan.spr.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.allan.spr.domain.enums.Perfil;

@Entity
@Table(name = "usuario")
@PrimaryKeyJoinColumn(name = "id_usuario")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario extends Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "senha")
	private String senha;
	
	@Transient
	private String confirmacaoSenha;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name = "perfis")
	private Set<Integer> perfis = new HashSet<Integer>();
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="usuario_presenca",
    joinColumns=@JoinColumn(name="id_usuario"),
    inverseJoinColumns=@JoinColumn(name="id_precenca"))
    private Set<ListaPresenca> listaPrensenca;
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(Perfil perfil) {
		this.perfis.add(perfil.getCod());
	}

	public Set<ListaPresenca> getListaPrensenca() {
		return listaPrensenca;
	}

	public void setListaPrensenca(Set<ListaPresenca> listaPrensenca) {
		this.listaPrensenca = listaPrensenca;
	}

	public void setPerfis(Set<Integer> perfis) {
		this.perfis = perfis;
	}
	
	

}
