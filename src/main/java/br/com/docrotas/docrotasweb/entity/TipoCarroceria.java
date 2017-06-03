package br.com.docrotas.docrotasweb.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoCarroceria {
	Nao_Aplicavel("00"),
	Aberta("01"),
	Fechada_Bau("02"),
	Graneleira("03"),
	Porta_Container("04"),
	Sider("05");
	
	private String codigo;
	
	@JsonValue
	public int toValue() {
		return ordinal();
	}	
	
	private TipoCarroceria(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}
	
}
