package br.com.docrotas.docrotasweb.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoCTe {
	NORMAL("0"),
	COMPLEMENTAR("1"),
	ANULACAO("2"),
	SUBSTITITUO("3");
	
	private String codigo;
	
	@JsonValue
	public int toValue() {
		return ordinal();
	}

	private TipoCTe(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}	

}
