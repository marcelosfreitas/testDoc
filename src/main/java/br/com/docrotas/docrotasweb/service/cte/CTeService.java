package br.com.docrotas.docrotasweb.service.cte;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import br.com.docrotas.docrotasweb.entity.CTe;
import br.com.docrotas.docrotasweb.entity.RetornoEnvioCTe;
import br.com.docrotas.docrotasweb.entity.SituacaoDocumento;
import br.com.docrotas.docrotasweb.entity.StatusProcessamento;
import br.com.docrotas.docrotasweb.entity.TipoAmbienteEmissao;
import br.com.docrotas.docrotasweb.repository.CTeRepository;
import br.com.docrotas.docrotasweb.utils.DocumentoEletronicoUtils;

@Service
public class CTeService {
	
	private static final Logger log = Logger.getLogger(CTeService.class);
	
	private static final String PATH_CERTIFICADO = "D:/certificado.pfx";
	private static final String SENHA_CERTIFICADO = "12345678";
	private static final String PATH_CACERTS = "D:/cacerts";
	private static final String CODIGO_UF = "31";
	private static final String VERSAO = "3.00";
	
	private static final String PATH_SCHEMA_ENVI_CTE = "D:\\workspace\\docrtoasweb2\\src\\main\\resources\\schema\\PL_CTe_300\\enviCTe_v3.00.xsd";
	private static final String PATH_SCHEMA_CONS_RECI_CTE = "D:\\workspace\\docrtoasweb2\\src\\main\\resources\\schema\\PL_CTe_300\\consReciCTe_v3.00.xsd";
	
	private static final String URL_CTE_RECEPCAO = "https://hcte.fazenda.mg.gov.br/cte/services/CteRecepcao";
	private static final String URL_CTE_RET_RECEPCAO = "https://hcte.fazenda.mg.gov.br/cte/services/CteRetRecepcao";
	
	@Autowired
	private CTeRepository cteRepository;
	
	public void buscarAutorizacao(Long id) throws Exception {
		CTe cte = cteRepository.findById(id);

		if (cte == null) {
			throw new Exception("Não foi encontrado um CT-e com ID = " + id);
		}

		if (SituacaoDocumento.DENEGADO.equals(cte.getSituacao())) {
			throw new Exception("Não e possível aprovar um CT-e denenegado");
		} else if (SituacaoDocumento.APROVADO.equals(cte.getSituacao())) {
			throw new Exception("Não foi possível buscar a aprovação deste CT-e, pois ele já esta aprovado");
		} else if (SituacaoDocumento.AGUARDANDO_AUTORIZACAO.equals(cte.getSituacao()) && StringUtils.isNotEmpty(cte.getProtocoloLote())) {
			consultarRetornoLote(cte);
		} else {
			buscarAutorizacao(cte);
		}
	}

	public void buscarAutorizacao(CTe cte) throws Exception {

		GeradorXmlCte geradorXmlCte = new GeradorXmlCte();
		XMLOutputter xmlOutputter = new XMLOutputter(Format.getCompactFormat());
		
		String xml = xmlOutputter.outputString(geradorXmlCte.getDocumentXML(cte));
		
		cte.setSituacao(SituacaoDocumento.PRONTO_PARA_ENVIAR);
		
		cte = cteRepository.save(cte);
		
		xml = xml.replaceAll("<CTe>", "<CTe xmlns=\"http://www.portalfiscal.inf.br/cte\" >");
		xml = xml.replaceAll("<enviCTe ", "<enviCTe xmlns=\"http://www.portalfiscal.inf.br/cte\" ");
		xml = xml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n","");

		AssinadorXMLCTe assinador = new AssinadorXMLCTe();
		xml = assinador.assinar(xml, PATH_CERTIFICADO, SENHA_CERTIFICADO, "CTe", "infCte");
		
		xml = xml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>","");
		
		try {
			validarSchema(xml, PATH_SCHEMA_ENVI_CTE);
		} catch (SAXException e) {
			cte.setSituacao(SituacaoDocumento.FALHA_VALIDACAO);
			cte = cteRepository.save(cte);
			throw new Exception(e);
		}
		
		URL url = new URL(URL_CTE_RECEPCAO);
		
		RecepcaoCTeService recepcaoCTeService = new RecepcaoCTeService(PATH_CERTIFICADO, PATH_CACERTS, SENHA_CERTIFICADO, url, CODIGO_UF, VERSAO);
		
		String retorno = recepcaoCTeService.comunicar(xml);
		
		log.info("XML retorno CTe Recepção: " + retorno.toString() + "\n");

		RetornoEnvioCTe retornoEnvio = processarRetornoEnvio(retorno);

		if (StatusProcessamento.LOTE_RECEBIDO.equals(retornoEnvio.getStatusProcessamento())) {
			cte.setSituacao(SituacaoDocumento.AGUARDANDO_AUTORIZACAO);
			cte.setProtocoloLote(retornoEnvio.getNumRecibo());
			cte.setDtProtocoloLote(retornoEnvio.getDtRecibemento());
			cte = cteRepository.save(cte);
		} else {
			cte.setSituacao(SituacaoDocumento.AGUARDANDO_CORRECAO);
			cte = cteRepository.save(cte);

			throw new Exception(retornoEnvio.getMotivo());
		}

		consultarRetornoLote(cte);
	}
	
	private RetornoEnvioCTe processarRetornoEnvio(String xml) throws JDOMException, IOException {
		RetornoEnvioCTe retornoEnvio = new RetornoEnvioCTe();
		
		InputSource inStream = new InputSource();  
		inStream.setCharacterStream(new StringReader(xml));  

		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(inStream);
		
		Element root = doc.getRootElement();
		
		Element body = root.getChildren().get(1);
		Element cteRecepcaoResult = body.getChildren().get(0);
		Element retEnviCte = cteRecepcaoResult.getChildren().get(0);
		
		Element tpAmb = retEnviCte.getChildren().get(0);
		retornoEnvio.setTipoAmbienteEmissao(TipoAmbienteEmissao.getTipoAmbienteEmissao(tpAmb.getTextTrim()));
		
		Element cUF = retEnviCte.getChildren().get(1);
		retornoEnvio.setCodUF(cUF.getTextTrim());
		
		Element verAplic = retEnviCte.getChildren().get(2);
		retornoEnvio.setVersao(verAplic.getTextTrim());
		
		Element cStat = retEnviCte.getChildren().get(3);
		retornoEnvio.setCodStatus(cStat.getTextTrim());
		retornoEnvio.setStatusProcessamento(StatusProcessamento.getStatusProcessamento(cStat.getTextTrim()));
		
		Element xMovito = retEnviCte.getChildren().get(4);
		retornoEnvio.setMotivo(xMovito.getTextTrim());
		
		Element infRec = retEnviCte.getChildren().get(5);

		Element nRec = infRec.getChildren().get(0);
		retornoEnvio.setNumRecibo(nRec.getTextTrim());
		
		Element dhRecbto = infRec.getChildren().get(1);
		retornoEnvio.setDtRecibemento(DocumentoEletronicoUtils.getDate(dhRecbto.getTextTrim()));

		Element tMed = infRec.getChildren().get(2);
		retornoEnvio.setTempoMedio(Integer.parseInt(tMed.getTextTrim()));

		return retornoEnvio;
	}

	public void consultarRetornoLote(CTe cte) throws Exception {
		
		if (SituacaoDocumento.AGUARDANDO_AUTORIZACAO.equals(cte.getSituacao())) {
			if (StringUtils.isNotEmpty(cte.getProtocoloLote())) {
				GeradorXmlCte geradorXmlCte = new GeradorXmlCte();
				XMLOutputter xmlOutputter = new XMLOutputter(Format.getCompactFormat());
				
				String xml = xmlOutputter.outputString(geradorXmlCte.getConsReciCTe(cte.getProtocoloLote(), cte.getTpAmbiente()));
				
				xml = xml.replaceAll("<consReciCTe ", "<consReciCTe xmlns=\"http://www.portalfiscal.inf.br/cte\" ");
				xml = xml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n","");
				
				validarSchema(xml, PATH_SCHEMA_CONS_RECI_CTE);
				
				URL url = new URL(URL_CTE_RET_RECEPCAO);
				
				RetornoRecepcaoCTeService retornoRecepcaoCTeService = new RetornoRecepcaoCTeService(PATH_CERTIFICADO, PATH_CACERTS, SENHA_CERTIFICADO, url, CODIGO_UF, VERSAO);
				
				String retorno = retornoRecepcaoCTeService.comunicar(xml);

				log.info("XML retorno CTe Retorno Recepção: " + retorno.toString() + "\n");	
			} else {
				throw new Exception("Não foi encontrado o número de recibo do protocolo de recimento do lote");
			}
			

		} else {
			throw new Exception("Não e possível consulta retorno de lote de um CT-e que esteja com situação diferente de 'Aguardando Autorização'");
		}
	}
	
	private String getNumeroRecibo(String xml) throws JDOMException, IOException {
		InputSource inStream = new InputSource();  
		inStream.setCharacterStream(new StringReader(xml));  

		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(inStream);
		
		Element root = doc.getRootElement();
		
		Element body = root.getChildren().get(1);
		Element cteRecepcaoResult = body.getChildren().get(0);
		Element retEnviCte = cteRecepcaoResult.getChildren().get(0);
		Element infRec = retEnviCte.getChildren().get(5);
		Element nRec = infRec.getChildren().get(0);

		return nRec.getText();
	}
	
	public void validarSchema(String xml, String pathSchema) throws SAXException, IOException {
		Source schemaFile = new StreamSource(pathSchema);
		Source xmlFile = new StreamSource(xml);
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(schemaFile);
		Validator validator = schema.newValidator();
		validator.validate(new StreamSource(new StringReader(xml)));
	}

}
