package br.com.docrotas.docrotasweb.service.cte;

import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.stereotype.Service;

import br.com.docrotas.docrotasweb.entity.CTe;

@Service
public class CTeService {
	
	public void buscarAutorizacao(CTe cte) throws Exception {
		
		GeradorXmlCte geradorXmlCte = new GeradorXmlCte();
		
		XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
		
		String xmlString = xmlOutputter.outputString(geradorXmlCte.getDocumentXML(cte));
		
		System.out.println(xmlString);
		
	}

}
