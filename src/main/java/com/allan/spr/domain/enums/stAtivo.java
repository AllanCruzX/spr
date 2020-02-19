package com.allan.spr.domain.enums;

import java.util.Arrays;
import java.util.List;

public enum stAtivo {
	
	ATIVO(1,"Ativo"),
	INATIVO(2, "INATIVO");
	
	private int cod;
	private String descricao;
	
	private stAtivo(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static List<stAtivo> valores() {
		return Arrays.asList(values());
	}
	
	
	public static stAtivo toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(stAtivo x : stAtivo.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
			
		}
		
		throw new IllegalArgumentException("Id invalido: " + cod);
		
	}
	
}
