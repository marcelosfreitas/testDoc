package br.com.docrotas.docrotasweb.service.cte;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AssinadorXML {
	
	private static final Logger log = Logger.getLogger(GeradorXmlCte.class);
	
	private PrivateKey privateKey;
	private KeyInfo keyInfo;
	
	public String assinarXML(String xml, String pathCertificado, String senha) throws Exception{
		Document document = documentFactory(xml);
		XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance("DOM");
		ArrayList<Transform> transformList = signatureFactory(signatureFactory);
		loadCertificates(pathCertificado, senha, signatureFactory);
		
		return assinar(signatureFactory, transformList, privateKey, keyInfo, document);
	}
	
	private ArrayList<Transform> signatureFactory(  
            XMLSignatureFactory signatureFactory)  
            throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {  
        ArrayList<Transform> transformList = new ArrayList<Transform>();  
        TransformParameterSpec tps = null;  
        Transform envelopedTransform = signatureFactory.newTransform(  
                Transform.ENVELOPED, tps);  
        Transform c14NTransform = signatureFactory.newTransform(  
                "http://www.w3.org/TR/2001/REC-xml-c14n-20010315", tps);  
  
        transformList.add(envelopedTransform);  
        transformList.add(c14NTransform);  
        return transformList;  
    }
	
	private void loadCertificates(String certificado, String senha,
			XMLSignatureFactory signatureFactory) throws Exception {

		InputStream entrada = new FileInputStream(certificado);
		KeyStore ks = KeyStore.getInstance("pkcs12");
		try {
			ks.load(entrada, senha.toCharArray());
		} catch (IOException e) {
			throw new Exception("Senha do Certificado Digital incorreta ou Certificado inválido.");
		}

		KeyStore.PrivateKeyEntry pkEntry = null;
		Enumeration<String> aliasesEnum = ks.aliases();
		while (aliasesEnum.hasMoreElements()) {
			String alias = (String) aliasesEnum.nextElement();
			if (ks.isKeyEntry(alias)) {
				pkEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(alias,
						new KeyStore.PasswordProtection(senha.toCharArray()));
				privateKey = pkEntry.getPrivateKey();
				break;
			}
		}

		X509Certificate cert = (X509Certificate) pkEntry.getCertificate();
		log.info("SubjectDN: " + cert.getSubjectDN().toString());

		KeyInfoFactory keyInfoFactory = signatureFactory.getKeyInfoFactory();
		List<X509Certificate> x509Content = new ArrayList<X509Certificate>();

		x509Content.add(cert);
		X509Data x509Data = keyInfoFactory.newX509Data(x509Content);
		keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(x509Data));
	}
	
	private String assinar(XMLSignatureFactory fac, ArrayList<Transform> transformList, PrivateKey privateKey, KeyInfo ki, Document document) throws Exception {
		NodeList elements = null;

		elements = document.getElementsByTagName("infCTe");

		org.w3c.dom.Element el = (org.w3c.dom.Element) elements.item(0);
		String id = el.getAttribute("Id");

		Reference ref = fac.newReference("#" + id,
				fac.newDigestMethod(DigestMethod.SHA1, null), transformList,
				null, null);

		SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(
				CanonicalizationMethod.INCLUSIVE,
				(C14NMethodParameterSpec) null), fac
				.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
				Collections.singletonList(ref));

		XMLSignature signature = fac.newXMLSignature(si, ki);

		DOMSignContext dsc = new DOMSignContext(privateKey, 
				document.getDocumentElement().getFirstChild());
		signature.sign(dsc);
		
		return outputXML(document);
	}
	
	private Document documentFactory(String xml) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder builder = factory.newDocumentBuilder();
		factory.setNamespaceAware(true);
		Document document = builder.parse(new ByteArrayInputStream(xml.getBytes()));
		return document;
	}
	
	private String outputXML(Document doc) throws TransformerException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(os));
		String xml = os.toString();
		if ((xml != null) && (!"".equals(xml))) {
			xml = xml.replaceAll("\\r\\n", "");
			xml = xml.replaceAll(" standalone=\"no\"", "");
		}
		return xml;
	}

}
