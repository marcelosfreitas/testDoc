package br.com.docrotas.docrotasweb.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoServico {
	NORMAL("0"),
	SUBCONTRATACAO("1"),
	REDESPACHO("2"),
	REDESPACHO_INTERMEDIARIO("3"),
	SERVICO_VINCULADO_MULTIMODAL("4");
	
	private String codigo;
	
	@JsonValue
	public int toValue() {
		return ordinal();
	}		

	private TipoServico(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}
		
}
