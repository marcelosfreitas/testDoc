package br.com.docrotas.docrotasweb.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoMedidas {
	M3("00"),
	KG("01"),
	TON("02"),
	UNIDADE("03"),
	LITROS("04"),
	MMBTU("05");
	
	private String codigo;
		
	@JsonValue
	public int toValue() {
		return ordinal();
	}

	public String getCodigo() {
		return codigo;
	}

	TipoMedidas(String codigo) {
		this.codigo = codigo;
	}
	
	public static TipoMedidas getTipoMedidas(int codigo) throws Exception{
		for(TipoMedidas tipoMedidas:TipoMedidas.values()){
			if(tipoMedidas.ordinal()==codigo){
				return tipoMedidas;
			}
		}
		
		throw new Exception("Não foi possível encontrar um tipo de medida com o código = " + String.valueOf(codigo));
	}
	
}
