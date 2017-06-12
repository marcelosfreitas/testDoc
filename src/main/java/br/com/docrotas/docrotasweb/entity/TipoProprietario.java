package br.com.docrotas.docrotasweb.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoProprietario {
	TAC_Agregado("0"), 
	TAC_Independente("1"), 
	Outros("2");
	
	private String codigo;
	
	@JsonValue
	public int toValue() {
		return ordinal();
	}

	public String getCodigo() {
		return codigo;
	}

	private TipoProprietario(String codigo) {
		this.codigo = codigo;
	}
		
}
