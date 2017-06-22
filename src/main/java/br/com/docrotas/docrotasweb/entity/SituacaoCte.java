package br.com.docrotas.docrotasweb.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SituacaoCte {

	EM_EDICAO,
	APROVADO,
	AGUARDANDO_CORRECAO;
	
	@JsonValue
	public int toValue() {
		return ordinal();
	}
}
