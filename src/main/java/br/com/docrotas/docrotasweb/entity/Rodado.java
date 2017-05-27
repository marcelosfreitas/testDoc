package br.com.docrotas.docrotasweb.entity;

public enum Rodado {
	Truck("01"), 
	Toco("02"), 
	Cavalo_Mecanico("03"),
	Van("04"),
	Utilitario("05"),
	Outros("06");
	
	private String codigo;

	Rodado(String codigo){
		this.codigo = codigo;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
}
