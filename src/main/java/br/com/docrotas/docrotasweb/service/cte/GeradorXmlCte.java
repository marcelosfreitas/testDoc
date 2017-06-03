package br.com.docrotas.docrotasweb.service.cte;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.sql.ordering.antlr.GeneratedOrderByLexer;
import org.jdom2.Document;
import org.jdom2.Element;

import br.com.docrotas.docrotasweb.entity.CTe;

public class GeradorXmlCte {
	
	public Document getDocumentXML(CTe cte) throws Exception{
		Document documentCte = null;
		
		if(cte == null) {
			throw new Exception("Cte não informado.");
		}
		
		Element infCte = new Element("infCte");
		
		infCte.addContent(getElementIde(cte));
		
		documentCte = new Document();
		documentCte.setRootElement(infCte);
		
		return documentCte;
	}

	private Element getElementIde(CTe cte) {
		Element elementIde = new Element("ide");
		
		Element elementCuf = new Element("cUF");
		elementCuf.addContent(String.valueOf(cte.getEmpresa().getCidade().getUf().getCodIBGE()));
		elementIde.addContent(elementCuf);
		
		Element elementCct = new Element("cCT");
		elementCct.addContent(StringUtils.leftPad(String.valueOf(cte.getId()),8,"0"));
		elementIde.addContent(elementCct);
		
		//cfop ?????
		
		//descricao cfop ?????
		
		
		return elementIde;
	}
}
