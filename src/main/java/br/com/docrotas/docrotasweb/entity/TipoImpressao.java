package br.com.docrotas.docrotasweb.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoImpressao {
	RETRATO("1"),
	PAISAGEM("2");

	private String codigo;
	
	@JsonValue
	public int toValue() {
		return ordinal();
	}

	public String getCodigo() {
		return codigo;
	}

	private TipoImpressao(String codigo) {
		this.codigo = codigo;
	}
	
}
