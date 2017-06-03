package br.com.docrotas.docrotasweb.entity;

public enum Medidas {
	M3("00"),
	KG("01"),
	TON("02"),
	UNIDADE("03"),
	LITROS("04"),
	MMBTU("05");
	
	private String codigo;

	private Medidas(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}
	
}
