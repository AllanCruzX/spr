package com.allan.spr.domain.enums;

import java.util.Arrays;
import java.util.List;

public enum TipoPresenca {
	
	PRESENTE(1,"Presente"),
	AUSENTE(2, "Ausente");
	
	private int cod;
	private String descricao;
	
	private TipoPresenca(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
 
	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static List<TipoPresenca> valores() {
		return Arrays.asList(values());
	}
	
	
	public static TipoPresenca toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(TipoPresenca x : TipoPresenca.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
			
		}
		
		throw new IllegalArgumentException("Id invalido: " + cod);
		
	}
	
}
