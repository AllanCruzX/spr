package com.allan.spr.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.allan.spr.domain.Usuario;
import com.allan.spr.services.validation.AlunoUpdate;

@AlunoUpdate
public class VoluntarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String cpf;

	@Email(message = "Email invalido")
	@NotEmpty(message = "Preenchimento obrigatório")
	private String email;

	private String telefone;

	public VoluntarioDTO() {

	}

	public VoluntarioDTO(Usuario voluntario) {

		id = voluntario.getId();
		nome = voluntario.getNome();
		cpf = voluntario.getCpf();
		telefone = voluntario.getTelefone() == null ? null : voluntario.getTelefone();
		email = voluntario.getEmail();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
