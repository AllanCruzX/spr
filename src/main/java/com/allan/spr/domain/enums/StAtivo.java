package com.allan.spr.domain.enums;

import java.util.Arrays;
import java.util.List;

public enum StAtivo {
	
	ATIVO(1,"ATIVO"),
	INATIVO(2, "INATIVO");
	
	private int cod;
	private String descricao;
	
	private StAtivo(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static List<StAtivo> valores() {
		return Arrays.asList(values());
	}
	
	
	public static StAtivo toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(StAtivo x : StAtivo.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
			
		}
		
		throw new IllegalArgumentException("Id invalido: " + cod);
		
	}
	
}
