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

import javax.xml.XMLConstants;
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
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

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
	private static final String PATH_CERTIFICADO = "C:/certificado.pfx";
	private static final String SENHA_CERTIFICADO = "12345678";
	private static final String PATH_ARQ_CARCETS = "C:/cacerts";

	
	public CTeComunicaoService2() throws IOException {

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
		System.setProperty("javax.net.ssl.keyStore", PATH_CERTIFICADO);
		System.setProperty("javax.net.ssl.keyStorePassword", SENHA_CERTIFICADO);
		/*
		* dados do certificado do servidor
		*/
		System.setProperty("javax.net.ssl.trustStoreType", "JKS");
		System.setProperty("javax.net.ssl.trustStore", PATH_ARQ_CARCETS);
		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");

		/* tipo de mensagem: SOAP */
		MimeHeaders header = new MimeHeaders();
		header.addHeader("Content-Type", "application/soap+xml");
		/* monta a mensagem SOAP */
		MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
		
		AssinarXMLsCertfificadoA1 assinarXMLsCertfificadoA1 = new AssinarXMLsCertfificadoA1();
		xml = assinarXMLsCertfificadoA1.assinaEnviCTe(xml, PATH_CERTIFICADO, SENHA_CERTIFICADO);
		xml = xml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>","");

		Source schemaFile = new StreamSource("C:\\Users\\lauro.chicorski\\Desktop\\PL_CTe_300\\PL_CTe_300\\enviCTe_v3.00.xsd");
		Source xmlFile = new StreamSource(xml);
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(schemaFile);
		Validator validator = schema.newValidator();
		validator.validate(new StreamSource(new StringReader(xml)));
		
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
		System.out.println("out :\n" + out.toString());
		}catch(Exception ex) {
		System.out.println("Erro: " + ex.getMessage() );
		}
	}
}
