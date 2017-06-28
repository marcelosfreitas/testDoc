package br.com.docrotas.docrotasweb.service.cte;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Security;

import javax.xml.XMLConstants;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;


public class CTeComunicaoService {
	
	private static final Logger log = Logger.getLogger(CTeComunicaoService.class);

	private String pathCertificado;
	private String senhaCertificado;
	private String pathCacerts;
	
    private static final String CTE = "CTe";
    private static final String INFCTE = "infCte";  

	private URL url;
	
	public CTeComunicaoService (String pathCertificado, String senhaCertificado, String pathCacerts) throws MalformedURLException {
		url = new URL("https://hcte.fazenda.mg.gov.br/cte/services/CteRecepcao");
		
		this.pathCertificado = pathCertificado;
		this.senhaCertificado = senhaCertificado;
		this.pathCacerts = pathCacerts;
	}

	public void recepicionarLote(String xml) throws Exception {
		try{
	
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
	
			/* tipo de mensagem: SOAP */
			MimeHeaders header = new MimeHeaders();
			header.addHeader("Content-Type", "application/soap+xml");
			/* monta a mensagem SOAP */
			MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
			
//			AssinadorXMLCTe assinarXMLsCertfificadoA1 = new AssinadorXMLCTe();
//			xml = assinarXMLsCertfificadoA1.assinar(xml, pathCertificado, senhaCertificado, CTE, INFCTE);
//			xml = xml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>","");
	
//			Source schemaFile = new StreamSource(path_schema);
//			Source xmlFile = new StreamSource(xml);
//			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//			Schema schema = schemaFactory.newSchema(schemaFile);
//			Validator validator = schema.newValidator();
//			validator.validate(new StreamSource(new StringReader(xml)));
			
			StringBuilder stb = new StringBuilder();
			stb.append("<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">");
			stb.append("<soap12:Header>");
			stb.append("<cteCabecMsg xmlns=\"http://www.portalfiscal.inf.br/cte/wsdl/CteRecepcao\">");
			stb.append("<cUF>31</cUF>");
			stb.append("<versaoDados>3.00</versaoDados>");
			stb.append("</cteCabecMsg>");
			stb.append("</soap12:Header>");
			stb.append("<soap12:Body>");
			stb.append("<cteDadosMsg xmlns=\"http://www.portalfiscal.inf.br/cte/wsdl/CteRecepcao\">");
			stb.append(xml);
			stb.append("</cteDadosMsg>");
			stb.append("</soap12:Body>");
			stb.append("</soap12:Envelope>");
			
			SOAPMessage message = factory.createMessage(header, new ByteArrayInputStream(stb.toString().getBytes()));
			/* instancia uma conexao SOAP */
			SOAPConnection conSoap = SOAPConnectionFactory.newInstance().createConnection();
			
			message.writeTo(System.out);
			
			/* Envia a mensagem SOAP ao WebService */
			SOAPMessage resWs = conSoap.call(message, url);
			
			resWs.getSOAPBody().toString();
			/* Mostra a mensagem de retorno */
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			resWs.writeTo(out);
	
			log.info(out.toString());
		}catch(Exception ex) {
			log.error(ex);
		}
	}
}
