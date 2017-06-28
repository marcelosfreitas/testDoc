package br.com.docrotas.docrotasweb.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import br.com.docrotas.docrotasweb.entity.StatusProcessamento;
import br.com.docrotas.docrotasweb.entity.TipoAmbienteEmissao;

public class RespostaRecepcao {

	private TipoAmbienteEmissao tipoAmbienteEmissao;
	private String codUF;
	private String versao;
	private StatusProcessamento statusProcessamento;
	private String codStatus;
	private String motivo;
	private String numRecibo;
	private Date dtRecibemento;
	private int tempoMedio;

	public TipoAmbienteEmissao getTipoAmbienteEmissao() {
		return tipoAmbienteEmissao;
	}
	public void setTipoAmbienteEmissao(TipoAmbienteEmissao tipoAmbienteEmissao) {
		this.tipoAmbienteEmissao = tipoAmbienteEmissao;
	}
	public String getCodUF() {
		return codUF;
	}
	public void setCodUF(String codUF) {
		this.codUF = codUF;
	}
	public String getVersao() {
		return versao;
	}
	public void setVersao(String versao) {
		this.versao = versao;
	}
	public StatusProcessamento getStatusProcessamento() {
		return statusProcessamento;
	}
	public String getCodStatus() {
		return codStatus;
	}
	public void setCodStatus(String codStatus) {
		if (StringUtils.isNotEmpty(codStatus)) {
			statusProcessamento = StatusProcessamento.getStatusProcessamento(codStatus);
		} else {
			statusProcessamento = null;
		}
		this.codStatus = codStatus;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getNumRecibo() {
		return numRecibo;
	}
	public void setNumRecibo(String numRecibo) {
		this.numRecibo = numRecibo;
	}
	public Date getDtRecibemento() {
		return dtRecibemento;
	}
	public void setDtRecibemento(Date dtRecibemento) {
		this.dtRecibemento = dtRecibemento;
	}
	public int getTempoMedio() {
		return tempoMedio;
	}
	public void setTempoMedio(int tempoMedio) {
		this.tempoMedio = tempoMedio;
	}
}
