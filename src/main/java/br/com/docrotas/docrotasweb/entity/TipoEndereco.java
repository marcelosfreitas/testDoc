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
	
	public static TipoEndereco geTipoEndereco(int value) throws Exception {
		for (TipoEndereco tipoEndereco : TipoEndereco.values()) {
			if (value == tipoEndereco.ordinal()) {
				return tipoEndereco;
			}
		}
		
		throw new Exception("Não foi possível identificar um Tipo de Endereço com valor = " + String.valueOf(value));
	}

}
