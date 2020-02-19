package com.allan.spr.domain.enums;

import java.util.Arrays;
import java.util.List;

public enum TipoAtividade {
	
	AULA(1,"Aula"),
	ACAO_SOCIAL(2, "Ação Social"),
	FESTA(3,"Festa"),
	PASSEIO(4,"Passeio");
	
	private int cod;
	private String descricao;
	
	private TipoAtividade(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static List<TipoAtividade> valores() {
		return Arrays.asList(values());
	}
	
	
	public static TipoAtividade toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(TipoAtividade x : TipoAtividade.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
			
		}
		
		throw new IllegalArgumentException("Id invalido: " + cod);
		
	}
	
}
