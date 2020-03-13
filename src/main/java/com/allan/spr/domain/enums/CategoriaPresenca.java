package com.allan.spr.domain.enums;

import java.util.Arrays;
import java.util.List;

public enum CategoriaPresenca {
	
	ALUNO(1,"Alunos"),
	VOLUNTARIO(2, "Voluntário");
	
	private int cod;
	private String descricao;
	
	private CategoriaPresenca(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
 
	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static List<CategoriaPresenca> valores() {
		return Arrays.asList(values());
	}
	
	
	public static CategoriaPresenca toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(CategoriaPresenca x : CategoriaPresenca.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
			
		}
		
		throw new IllegalArgumentException("Id invalido: " + cod);
		
	}
	
}
