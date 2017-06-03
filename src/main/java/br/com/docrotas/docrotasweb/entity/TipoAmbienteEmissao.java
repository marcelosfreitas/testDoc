package br.com.docrotas.docrotasweb.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoAmbienteEmissao {
	HOMOLOGACAO(2), 
	PRODUCAO(1);
	
	private int codigo;	
	
	@JsonValue
	public int toValue() {
		return ordinal();
	}

	public int getCodigo() {
		return codigo;
	}

	private TipoAmbienteEmissao(int codigo) {
		this.codigo = codigo;
	}
		
}
