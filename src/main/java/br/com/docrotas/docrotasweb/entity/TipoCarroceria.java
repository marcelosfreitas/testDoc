package br.com.docrotas.docrotasweb.entity;

public enum Carroceria {
	Nao_Aplicavel("00"),
	Aberta("01"),
	Fechada_Bau("02"),
	Graneleira("03"),
	Porta_Container("04"),
	Sider("05");
	
	private String codigo;	
	
	private Carroceria(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}
	
}
