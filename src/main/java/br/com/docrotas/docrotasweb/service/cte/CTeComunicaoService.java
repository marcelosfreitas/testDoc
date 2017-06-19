package br.com.docrotas.docrotasweb.service.cte;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPElement;
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

public class CTeComunicaoService {
	
//	private URL url = new URL("https://hcte.fazenda.mg.gov.br/cte/services/CteRecepcao");
	private URL url = new URL("	https://cte.fazenda.mg.gov.br/cte/services/CteRecepcao");
	private Service service;
	private String operationName = "cteRetRecepcao";
	private String soapActionURI = "cteRecepcaoLote";
	private String namespace = "http://www.portalfiscal.inf.br/cte/wsdl/CteRecepcao";
	private String inputCabMsg = "cteCabecMsg";
	private byte[] wsddBytes;
	private String xmlElementRetName = "cteRecepcaoLoteResult";
	
	public CTeComunicaoService() throws IOException {
		InputStream input = null;

		try {
			input = this.getClass().getResourceAsStream("/br/com/docrotas/docrotasweb/service/cte/client-config.wsdd");

			if (input == null) {
				throw new RuntimeException(String.format("Arquivo de configuração '%s' não encontrato client-config.wsdd"));
			}

			byte[] b = new byte[2048];
			int lidos = input.read(b);
			wsddBytes = Arrays.copyOf(b, lidos);
		} finally {
			input.close();
		}
		FileProvider fileProvider = new FileProvider(new ByteArrayInputStream(wsddBytes));
		
		this.service = new Service(fileProvider);
	}
	
	public void recepicionarLote(String xml) throws Exception {
		executeWs(xml, "2.00", "31");
	}
	
	private String executeWs(String xml, String versaoDados, String codigoUf) throws Exception {
		System.clearProperty("javax.net.ssl.keyStore");
		System.clearProperty("javax.net.ssl.keyStorePassword");
		System.clearProperty("javax.net.ssl.trustStore");
		
		System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
		System.setProperty("javax.net.ssl.keyStore", "D:/certificado.pfx");
		System.setProperty("javax.net.ssl.keyStorePassword", "12345678");
		
		System.setProperty("javax.net.ssl.trustStoreType", "JKS");
		System.setProperty("javax.net.ssl.trustStore", "D:/cacerts");
		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
		
		Call           call = createCall();
        QName          qName = new QName(namespace, inputCabMsg);
        MessageElement cabcMsg = new MessageElement(qName);
        cabcMsg.setPrefix("cter");

        SOAPElement versaoDadosElem = new MessageElement(namespace, "versaoDados");
        SOAPElement cUFElem = new MessageElement(namespace, "cUF");
        versaoDadosElem.addTextNode(versaoDados);
        cUFElem.addTextNode(codigoUf);
        cabcMsg.addChild(( MessageElement ) cUFElem);
        cabcMsg.addChild(( MessageElement ) versaoDadosElem);
        call.addHeader(new SOAPHeaderElement(cabcMsg));

        /*MessageElement actionElement = new SOAPBodyElement(getNamespace(), getOperationName());
        actionElement.setPrefix("");*/
        MessageElement actionElement = new SOAPBodyElement(namespace, inputCabMsg);
        
        actionElement.setPrefix("cter");

        Document       doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
        MessageElement me = new MessageElement(doc.getDocumentElement());
        //actionElement.setValue(xmlEnvio);
        actionElement.addChild(me);

        /*MessageElement xmlDados = new SOAPBodyElement(getNameSpaceParamIn(), getInputDadosMsg());
        xmlDados.setValue(xmlEnvio);
        actionElement.addChild(xmlDados);*/
        MessageElement [] input = new MessageElement[ 1 ];
        input[ 0 ] = actionElement;

        Object retWs = null;

        retWs = call.invoke(input);
        /*try {
        } catch(AxisFault e) {
            e.printStackTrace();

            RuntimeException re = null;

            if(e.getCause() instanceof SAXException) {
                System.out.println(e.getFaultReason());

                re = new RuntimeException("Erro na resposta do webservice. Não foi retornado um xml válido.");
            }

            if(re == null) {
                throw e;
            } else {
                re.initCause(e);
                throw re;
            }
        }*/

//        if(retWs instanceof RemoteException) {
//            throw ( RemoteException ) retWs;
//        }

        SOAPBodyElement elem = null;
        Vector          elems = ( Vector ) retWs;
        elem = ( SOAPBodyElement ) elems.get(0);

        return getResponseElement(elem);

        //throw new Exception("Retorno inválido do webservice");
    }
	
	protected Call createCall() throws Exception {

        Call call = ( Call ) service.createCall();
        call.setTargetEndpointAddress(url);

        call.setOperation(createOperation());
        call.setUseSOAPAction(true);
        call.setSOAPActionURI(soapActionURI);
        call.setEncodingStyle(org.apache.axis.Constants.URI_SOAP12_ENC);
        call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
        call.setProperty(AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        call.setSOAPVersion(SOAP12Constants.SOAP12_CONSTANTS);
        call.setOperationName(new QName("", operationName));

        return call;
    }
	
	private OperationDesc createOperation() {
        OperationDesc oper = new OperationDesc();
        oper.setName(operationName);
        oper.setReturnClass(String.class);

        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);

        return oper;
    }
	
    protected String getResponseElement(SOAPBodyElement elem) throws Exception {
        if(elem.getName().equals(xmlElementRetName)) {
            return elementToString((MessageElement) elem.getFirstChild());
        } else {
            Iterator<?> ite = elem.getChildElements();

            while(ite.hasNext()) {
                Object obj = ite.next();

                if(obj instanceof MessageElement) {
                    MessageElement mElem = ( MessageElement ) obj;

                    if(mElem.getName().equals(xmlElementRetName)) {
                        return elementToString(mElem);
                    }
                }
            }
        }

        throw new Exception("Retorno inválido do webservice. Não encontrado o elemento: " + xmlElementRetName);
    }
    
    private String elementToString(MessageElement elem) throws TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException, UnsupportedEncodingException {
        //Source                source = new DOMSource(elem.getFirstChild());
        Source                source = new DOMSource(elem);
        ByteArrayOutputStream xmlResultOut = new ByteArrayOutputStream(2048);
        Result                result = null;
        result = new StreamResult(xmlResultOut);

        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        xformer.transform(source, result);

        String ret = new String(xmlResultOut.toByteArray(), "UTF-8");

        return ret;
    }

}
