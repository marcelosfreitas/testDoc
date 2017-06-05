package br.com.docrotas.docrotasweb.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoProprietario {
	TAC_Agregado(0), 
	TAC_Independente(1), 
	Outros(2);
	
	private int codigo;
	
	@JsonValue
	public int toValue() {
		return ordinal();
	}

	public int getCodigo() {
		return codigo;
	}

	private TipoProprietario(int codigo) {
		this.codigo = codigo;
	}
		
}
