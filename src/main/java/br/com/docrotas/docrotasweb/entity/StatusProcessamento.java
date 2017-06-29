package br.com.docrotas.docrotasweb.entity;

public enum StatusProcessamento {
	
	AUTORIZADO("100"),
	CANCELAMENTO_HOMOLOGADO("101"),
	INUTILIZACAO_HOMOLOGADO("102"),
	LOTE_RECEBIDO("103"),
	LOTE_PROCESSADO("104"),
	LOTE_PROCESSAMENTO("105"),
	LOTE_NAO_LOCALIZADO("106"),
	SERVICO_OPERACAO("107"),
	SERVICO_PARALISADO_MOMENTANEAMENTE("108"),
	SERVICO_PARALISADO_SEM_PREVISAO("109"),
	USO_DENEGADO("110"),
	CONS_CAD_COM_UMA_OCORRENCIA("111"),
	CONS_CAD_COM_MAIS_OCORRENCIA("112"),
	SVC_OPERACAO("113"),
	SVC_DESABILITADA("114"),
	EVENTO_REGISTRADO_VINCULADO_ALERTA("134"),
	EVENTO_REGISTRADO_VINCULADO("135"),
	EVENTO_REGISTRADO_NAO_VINCULADO("136"),
	NAO_ATENDIDO("999");

	private String codigo;

	public String getCodigo() {
		return codigo;
	}

	private StatusProcessamento(String codigo) {
		this.codigo = codigo;
	}

	public static StatusProcessamento getStatusProcessamento(String codigo) {
		for (StatusProcessamento status : StatusProcessamento.values()) {
			if (status.getCodigo().equals(codigo)) {
				return status;
			}
		}

		return StatusProcessamento.NAO_ATENDIDO;
	}

}
