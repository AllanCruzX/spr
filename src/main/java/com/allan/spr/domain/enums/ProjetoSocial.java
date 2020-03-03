package com.allan.spr.domain.enums;

import java.util.Arrays;
import java.util.List;

public enum ProjetoSocial {
	
	REINTREGAR_KIDS_JAMAICA(1,"Reintegrar Kids Jamaica");
	
	
	private int cod;
	private String descricao;
	
	private ProjetoSocial(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static List<ProjetoSocial> valores() {
		return Arrays.asList(values());
	}
	
	
	public static ProjetoSocial toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(ProjetoSocial x : ProjetoSocial.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
			
		}
		
		throw new IllegalArgumentException("Id invalido: " + cod);
		
	}
	
}
