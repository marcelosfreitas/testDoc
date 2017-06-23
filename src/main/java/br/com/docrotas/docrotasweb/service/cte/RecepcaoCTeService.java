package br.com.docrotas.docrotasweb.service.cte;

import java.net.URL;

import br.com.docrotas.docrotasweb.service.ComunicacaoSefazService;

public class RecepcaoCTeService extends ComunicacaoSefazService{
	private String codigoUF;
	private String versao;

	public RecepcaoCTeService(String pathCertificado, String pathCacerts, String senhaCertificado, URL url, String codigoUF, String versao) {
		super(pathCertificado, pathCacerts, senhaCertificado, url);
		this.codigoUF = codigoUF;
		this.versao = versao;
	}

	@Override
	public String getNamespace() {
		return "http://www.portalfiscal.inf.br/cte/wsdl/CteRecepcao";
	}

	@Override
	public String getNomeTagCabec() {
		return "cteCabecMsg";
	}

	@Override
	public String getNomeTagDados() {
		return "cteDadosMsg";
	}

	@Override
	public String getCabecalho() {
		StringBuilder stb = new StringBuilder();
		stb.append("<cUF>");
		stb.append(codigoUF);
		stb.append("</cUF>");
		stb.append("<versaoDados>");
		stb.append(versao);
		stb.append("</versaoDados>");
		return stb.toString();
	}

}
