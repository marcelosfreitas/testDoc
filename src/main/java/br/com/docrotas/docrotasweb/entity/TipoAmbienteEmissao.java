package br.com.docrotas.docrotasweb.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoAmbienteEmissao {

	PRODUCAO("1"),
	HOMOLOGACAO("2");
	
	private String codigo;	
	
	@JsonValue
	public int toValue() {
		return ordinal();
	}

	public String getCodigo() {
		return codigo;
	}

	private TipoAmbienteEmissao(String codigo) {
		this.codigo = codigo;
	}
	
	public static TipoAmbienteEmissao getTipoAmbienteEmissao(String codigo) {
		if ("2".equals(codigo)) {
			return TipoAmbienteEmissao.HOMOLOGACAO;
		} else {
			return TipoAmbienteEmissao.PRODUCAO;
		}
	}
		
}
