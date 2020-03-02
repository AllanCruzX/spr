package com.allan.spr.domain.enums;

import java.util.Arrays;
import java.util.List;

public enum StSimNao {
	
	SIM(1,"Sim"),
	NAO(2, "Não");
	
	private int cod;
	private String descricao;
	
	private StSimNao(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static List<StSimNao> valores() {
		return Arrays.asList(values());
	}
	
	
	public static StSimNao toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(StSimNao x : StSimNao.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
			
		}
		
		throw new IllegalArgumentException("Id invalido: " + cod);
		
	}
	
}
