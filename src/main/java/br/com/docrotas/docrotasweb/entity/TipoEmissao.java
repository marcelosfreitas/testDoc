package br.com.docrotas.docrotasweb.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoEmissao {
	NORMAL("1"),
	EPEC_SVC("4"),
	CONTINGENCIA_FSDA("5"),
	SVC_RS("7"),
	SVC_SP("8");
	
	private String codigo;
	
	@JsonValue
	public int toValue() {
		return ordinal();
	}	

	private TipoEmissao(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}	

}
