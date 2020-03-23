package com.allan.spr.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.allan.spr.domain.Usuario;
import com.allan.spr.services.validation.AlunoUpdate;

@AlunoUpdate
public class UsuarioLogadoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;

	@Email(message = "Email invalido")
	private String email;
	
	public UsuarioLogadoDTO() {

	}

	public UsuarioLogadoDTO(Usuario usuario) {

		id = usuario.getId();
		nome = usuario.getNome();
		email =  usuario.getEmail();

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
