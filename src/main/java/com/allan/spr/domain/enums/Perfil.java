package com.allan.spr.domain.enums;

public enum Perfil {
	//ROLE_ - esse prefixo é egisencia do spring security
	ADMIN(1, "ROLE_ADMIN"),
	VOLUNTARIO(2, "ROLE_VOLUNTARIO"),
	ALUNO(3,"ROLE_ALUNO");

	private int cod;
	private String descricao;

	private Perfil(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Perfil toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (Perfil x : Perfil.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}

		}

		throw new IllegalArgumentException("Id invalido: " + cod);

	}

}
