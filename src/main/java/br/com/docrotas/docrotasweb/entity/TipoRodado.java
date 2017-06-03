package br.com.docrotas.docrotasweb.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoRodado {
	Truck("01"), 
	Toco("02"), 
	Cavalo_Mecanico("03"),
	Van("04"),
	Utilitario("05"),
	Outros("06");
	
	private String codigo;
	
	@JsonValue
	public int toValue() {
		return ordinal();
	}	

	TipoRodado(String codigo){
		this.codigo = codigo;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
}
