package br.com.docrotas.docrotasweb.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoEndereco {
	
	PRINCIPAL, 	// 0
	COBRANCA,	// 1
	ENTREGA;	// 2
	
	@JsonValue
	public int toValue() {
		return ordinal();
	}

}
