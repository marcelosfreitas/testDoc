package br.com.docrotas.docrotasweb.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.Security;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public abstract class ComunicacaoSefazService {
	
	private String pathCertificado;
	private String pathCacerts;
	private String senhaCertificado;
	private URL url;

	public abstract String getNamespace();

	public abstract String getNomeTagCabec();
	
	public abstract String getNomeTagDados();

	public abstract String getCabecalho();

	public ComunicacaoSefazService(String pathCertificado, String pathCacerts, String senhaCertificado, URL url) {
		this.pathCertificado = pathCertificado;
		this.pathCacerts = pathCacerts;
		this.senhaCertificado = senhaCertificado;
		this.url = url;
	}
	
	public String comunicar(String xml) throws SOAPException, IOException {
		setSystemProperties();
		
		String retorno = null;

		/* tipo de mensagem: SOAP */
		MimeHeaders header = new MimeHeaders();
		header.addHeader("Content-Type", "application/soap+xml");
		/* monta a mensagem SOAP */
		MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
		
		SOAPMessage message = factory.createMessage(header, new ByteArrayInputStream(getSOAPText(xml).getBytes()));
		/* instancia uma conexao SOAP */
		SOAPConnection conSoap = SOAPConnectionFactory.newInstance().createConnection();
		
		message.writeTo(System.out);
		
		/* Envia a mensagem SOAP ao WebService */
		SOAPMessage resWs = conSoap.call(message, url);
		
		resWs.getSOAPBody().toString();
		/* Mostra a mensagem de retorno */
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		resWs.writeTo(out);
		
		return retorno;
	}
	
	public void setSystemProperties() {
		System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

		/*
		* limpo propriedades
		*/
		System.clearProperty("javax.net.ssl.keyStore");
		System.clearProperty("javax.net.ssl.keyStorePassword");
		System.clearProperty("javax.net.ssl.trustStore");
		/*
		* dados do certificado do cliente
		*/
		System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
		System.setProperty("javax.net.ssl.keyStore", pathCertificado);
		System.setProperty("javax.net.ssl.keyStorePassword", senhaCertificado);
		/*
		* dados do certificado do servidor
		*/
		System.setProperty("javax.net.ssl.trustStoreType", "JKS");
		System.setProperty("javax.net.ssl.trustStore", pathCacerts);
		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");

	}

	public String getSOAPText(String xml) {
		StringBuilder stb = new StringBuilder();
		stb.append("<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">");
		stb.append("<soap12:Header>");
		stb.append("<");
		stb.append(getNomeTagCabec());
		stb.append(" xmlns=\"");
		stb.append(getNamespace());
		stb.append("\">");
		stb.append(getCabecalho());
		stb.append("</");
		stb.append(getNomeTagCabec());
		stb.append(">");
		stb.append("</soap12:Header>");
		stb.append("<soap12:Body>");
		stb.append("<");
		stb.append(getNomeTagDados());
		stb.append(" xmlns=\"");
		stb.append(getNamespace());
		stb.append("\">");
		stb.append(xml);
		stb.append("</");
		stb.append(getNomeTagDados());
		stb.append(">");
		stb.append("</soap12:Body>");
		stb.append("</soap12:Envelope>");
		
		return stb.toString();
	}
}
