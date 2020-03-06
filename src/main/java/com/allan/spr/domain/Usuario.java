package com.allan.spr.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.allan.spr.domain.enums.Perfil;
import com.allan.spr.domain.enums.ProjetoSocial;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "usuario")
@PrimaryKeyJoinColumn(name = "id_usuario")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario extends PessoaFisica implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@Column(name = "senha")
	private String senha;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "perfis")
	private Set<Integer> perfis = new HashSet<Integer>();
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "projeto_social")
	private ProjetoSocial projetoSocial;

	public ProjetoSocial getProjetoSocial() {
		return projetoSocial;
	}

	public void setProjetoSocial(ProjetoSocial projetoSocial) {
		this.projetoSocial = projetoSocial;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(Perfil perfil) {
		this.perfis.add(perfil.getCod());
	}

}
