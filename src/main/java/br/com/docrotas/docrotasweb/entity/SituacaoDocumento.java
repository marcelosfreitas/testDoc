package br.com.docrotas.docrotasweb.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SituacaoDocumento {

	EM_EDICAO,
	APROVADO,
	AGUARDANDO_CORRECAO,
	FALHA_VALIDACAO,
	PRONTO_PARA_ENVIAR,
	AGUARDANDO_AUTORIZACAO,
	DENEGADO;

	@JsonValue
	public int toValue() {
		return ordinal();
	}
}
