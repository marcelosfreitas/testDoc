package br.com.docrotas.docrotasweb.service.cte;

import javax.annotation.Resource;

import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.docrotas.docrotasweb.entity.CTe;
import br.com.docrotas.docrotasweb.repository.CTeRepository;

@Service
public class CTeService {
	
	private String pathCertificado = "C:/certificado.pfx";
	private String senhaCertificado = "12345678";
	private String pathCacerts = "C:/cacerts";
	
	@Autowired
	private CTeRepository cteRepository;
	
	public void buscarAutorizacao(Long id) throws Exception {
		CTe cte = cteRepository.findById(id);

		if (cte == null) {
			throw new Exception("Não foi encontrado um CT-e com ID = " + id);
		}

		buscarAutorizacao(cte);
	}

	public void buscarAutorizacao(CTe cte) throws Exception {
		GeradorXmlCte geradorXmlCte = new GeradorXmlCte();
		
		XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
		
		String xmlString = xmlOutputter.outputString(geradorXmlCte.getDocumentXML(cte));
		
		xmlString = xmlString.replaceAll("<CTe>", "<CTe xmlns=\"http://www.portalfiscal.inf.br/cte\" >");
		xmlString = xmlString.replaceAll("<enviCTe ", "<enviCTe xmlns=\"http://www.portalfiscal.inf.br/cte\" ");
		xmlString = xmlString.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n","");

		CTeComunicaoService cTeComunicaoService = new CTeComunicaoService(pathCertificado,senhaCertificado,pathCacerts);
		
		cTeComunicaoService.recepicionarLote(xmlString);
		
	}
	
	

}
