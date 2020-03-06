package com.allan.spr.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.allan.spr.domain.Usuario;
import com.allan.spr.services.validation.AlunoInsert;
import com.allan.spr.services.validation.VoluntarioInsert;

@VoluntarioInsert
public class VoluntarioNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String cpf;

	@Email(message = "Email invalido")
	@NotEmpty(message = "Preenchimento obrigatório")
	private String email;

	private String telefone;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	private String senha;

	public VoluntarioNewDTO() {

	}

	public VoluntarioNewDTO(Usuario voluntario) {

		nome = voluntario.getNome();
		cpf = voluntario.getCpf();
		telefone = voluntario.getTelefone() == null ? null : voluntario.getTelefone();
		email = voluntario.getEmail();
		senha = voluntario.getSenha();

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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	

}
