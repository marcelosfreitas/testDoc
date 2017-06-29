package br.com.docrotas.docrotasweb.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import br.com.docrotas.docrotasweb.entity.StatusProcessamento;
import br.com.docrotas.docrotasweb.entity.TipoAmbienteEmissao;

public class RespostaRetornoRecepcao {

	private TipoAmbienteEmissao tipoAmbienteEmissao;
	private String codUF;
	private String versao;
	private StatusProcessamento statusProcessamento;
	private String codStatus;
	private String motivo;
	private String numRecibo;
	private Date dtRecebimento;
	private String numProtocolo;
	private StatusProcessamento statusProcessamentoCTe;
	private String codStatusCTe;
	private String motivoCTe;

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
	public Date getDtRecebimento() {
		return dtRecebimento;
	}
	public void setDtRecebimento(Date dtRecebimento) {
		this.dtRecebimento = dtRecebimento;
	}
	public String getNumProtocolo() {
		return numProtocolo;
	}
	public void setNumProtocolo(String numProtocolo) {
		this.numProtocolo = numProtocolo;
	}
	public StatusProcessamento getStatusProcessamentoCTe() {
		return statusProcessamentoCTe;
	}

	public String getCodStatusCTe() {
		return codStatusCTe;
	}

	public void setCodStatusCTe(String codStatusCTe) {
		if (StringUtils.isNotEmpty(codStatusCTe)) {
			statusProcessamentoCTe = StatusProcessamento.getStatusProcessamento(codStatusCTe);
		} else {
			statusProcessamentoCTe = null;
		}
		
		this.codStatusCTe = codStatusCTe;
	}
	public String getMotivoCTe() {
		return motivoCTe;
	}
	public void setMotivoCTe(String motivoCTe) {
		this.motivoCTe = motivoCTe;
	}
}
