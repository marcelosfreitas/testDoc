package br.com.docrotas.docrotasweb.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoCST {
	TRIBUTACAO_NORMAL("00"),
	TRIBUTACAO_REDUZIDA("20"),
	ICMS_ISENCAO("40"),
	ICMS_NAO_TRIBUTADA("41"),
	ICMS_DIFERIDO("51"),
	ICMS_SUBSTITUICAO_TRIBUTARIA("60"),
	ICMS_OUTROS("90"),
	ICMS_OUTRA_UF("90"),
	ICMS_SIMPLES_NACIONAL("90");
	
	private String codigo;
	
	@JsonValue
	public int toValue() {
		return ordinal();
	}	

	private TipoCST(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}
	
}
