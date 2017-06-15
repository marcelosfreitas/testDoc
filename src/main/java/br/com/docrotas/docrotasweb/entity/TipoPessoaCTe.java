package br.com.docrotas.docrotasweb.entity;

public enum TipoPessoaCTe {
	EMITENTE("emit"),
	REMETENTE("rem"),
	DESTINATARIO("dest"),
	EXPEDIDOR("exped"),
	RECEBEDOR("receb");
	
	private String tag;

	public String getTag() {
		return tag;
	}
	
	private TipoPessoaCTe(String tag){
		this.tag = tag;
	}

}
