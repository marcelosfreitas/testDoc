package br.com.docrotas.docrotasweb.service.cte;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.Security;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.axis.AxisEngine;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.message.MessageElement;
import org.apache.axis.message.SOAPBodyElement;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.axis.soap.SOAP12Constants;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class CTeComunicaoService2 {
	
	private URL url = new URL("https://hcte.fazenda.mg.gov.br/cte/services/CteRecepcao");
//	private Service service;
//	private String operationName = "cteRetRecepcao";
//	private String soapActionURI = "cteRecepcaoLoteResult";
//	private String namespace = "http://www.portalfiscal.inf.br/cte/wsdl/CteRecepcao";
//	private String inputCabMsg = "cteCabecMsg";
//	private byte[] wsddBytes;
//	private String xmlElementRetName = "cteRecepcaoLoteResult";
	
	public CTeComunicaoService2() throws IOException {
//		InputStream input = null;
//
//		try {
//			input = this.getClass().getResourceAsStream("/br/com/docrotas/docrotasweb/service/cte/client-config.wsdd");
//
//			if (input == null) {
//				throw new RuntimeException(String.format("Arquivo de configuração '%s' não encontrato client-config.wsdd"));
//			}
//
//			byte[] b = new byte[2048];
//			int lidos = input.read(b);
//			wsddBytes = Arrays.copyOf(b, lidos);
//		} finally {
//			input.close();
//		}
//		FileProvider fileProvider = new FileProvider(new ByteArrayInputStream(wsddBytes));
//		
//		this.service = new Service(fileProvider);
	}
	
	public void recepicionarLote(String xml) throws Exception {
		try
		{
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
		System.setProperty("javax.net.ssl.keyStore", "D:/certificado.pfx");
		System.setProperty("javax.net.ssl.keyStorePassword", "12345678");
		/*
		* dados do certificado do servidor
		*/
		System.setProperty("javax.net.ssl.trustStoreType", "JKS");
		System.setProperty("javax.net.ssl.trustStore", "D:/cacerts");
		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
		/* Url do WebService */
		URL url = new URL("https://hcte.fazenda.mg.gov.br/cte/services/CteRecepcao");
		/* tipo de mensagem: SOAP */
		MimeHeaders header = new MimeHeaders();
		header.addHeader("Content-Type", "application/soap+xml");
		/* monta a mensagem SOAP */
		MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
		SOAPMessage message = factory.createMessage(header, new ByteArrayInputStream(xml.getBytes()) );
		/* instancia uma conexao SOAP */
		SOAPConnection conSoap = SOAPConnectionFactory.newInstance().createConnection();
		/* Envia a mensagem SOAP ao WebService */
		SOAPMessage resWs = conSoap.call(message, url);
		/* Mostra a mensagem de retorno */
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		resWs.writeTo(out);
		System.out.println("out :\n" + out.toString());
		}catch(Exception ex) {
		System.out.println("Erro: " + ex.getMessage() );
		}
	}
}
