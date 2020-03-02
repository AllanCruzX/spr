package com.allan.spr.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.allan.spr.domain.Usuario;
import com.allan.spr.services.validation.AlunoUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;

@AlunoUpdate
public class AlunoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;

	private String nomePai;

	private String nomeMae;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String nomeResponsavel;

	private String cpf;

	@Email(message = "Email invalido")
	private String email;

	private String telefone;

	@NotNull(message = "Preenchimento obrigatório")
	private int pcd;

	private String cep;

	private String bairro;

	private String logradouro;

	private String numero;

	private Long cidadeId;

	private Long enderecoId;

	@JsonFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dataNsacimento;

	public AlunoDTO() {

	}

	public AlunoDTO(Usuario aluno) {

		id = aluno.getId();
		nome = aluno.getNome();
		nomeMae = aluno.getNomeMae() == null ? null : aluno.getNomeMae();
		nomePai = aluno.getNomePai() == null ? null : aluno.getNomePai();
		nomeResponsavel = aluno.getNomeResponsavel();
		telefone = aluno.getTelefone() == null ? null : aluno.getTelefone();
		email = aluno.getEmail() == null ? null : aluno.getEmail();
		pcd = aluno.getPcd().getCod();
		dataNsacimento = aluno.getDataNsacimento() == null ? null : aluno.getDataNsacimento();

		enderecoId = aluno.getEndereco().getId() == null ? null : aluno.getEndereco().getId();
		cep = aluno.getEndereco().getCep() == null ? null : aluno.getEndereco().getCep();
		bairro = aluno.getEndereco().getBairro() == null ? null : aluno.getEndereco().getBairro();
		logradouro = aluno.getEndereco().getLogradouro() == null ? null : aluno.getEndereco().getLogradouro();
		numero = aluno.getEndereco().getNumero() == null ? null : aluno.getEndereco().getNumero();
		cidadeId = aluno.getEndereco().getCidade() == null ? null : aluno.getEndereco().getCidade().getId();

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
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

	public int getPcd() {
		return pcd;
	}

	public void setPcd(int pcd) {
		this.pcd = pcd;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Long getCidadeId() {
		return cidadeId;
	}

	public void setCidadeId(Long cidadeId) {
		this.cidadeId = cidadeId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEnderecoId() {
		return enderecoId;
	}

	public void setEnderecoId(Long enderecoId) {
		this.enderecoId = enderecoId;
	}

	public Date getDataNsacimento() {
		return dataNsacimento;
	}

	public void setDataNsacimento(Date dataNsacimento) {
		this.dataNsacimento = dataNsacimento;
	}

}
