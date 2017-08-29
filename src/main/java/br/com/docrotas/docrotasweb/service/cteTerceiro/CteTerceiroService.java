package br.com.docrotas.docrotasweb.service.cteTerceiro;

import org.jdom2.Document;
import org.springframework.stereotype.Service;

@Service
public class CteTerceiroService {
	
	public String recepcionar(String txtCte) {
		String protocolo = null;
		
//	    Salvar o txt no banco de dados e retornar o protocolo;
//		Será aberto um thread para transformar o txt em xml e comunicar com a Sefaz

		GeradorXMLTerceiro gerador = new GeradorXMLTerceiro();
		
		Document xml = gerador.getDocumentXML(txtCte);
		
		return protocolo;
	}

}
