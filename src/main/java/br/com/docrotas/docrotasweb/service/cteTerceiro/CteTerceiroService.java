package br.com.docrotas.docrotasweb.service.cteTerceiro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.jdom2.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.docrotas.docrotasweb.entity.CteTerceiro;
import br.com.docrotas.docrotasweb.repository.CteTerceiroRepository;

@Service
public class CteTerceiroService {
	
	@Autowired
	private CteTerceiroRepository cteTerceiroRepository;
	
	public String recepcionar(String txtCte) {
		String protocolo = "123456";
		
//	    Salvar o txt no banco de dados e retornar o protocolo;
		CteTerceiro cteTerceiro = new CteTerceiro();
		
		cteTerceiro.setId(123456L);
		cteTerceiro.setTxTerceiro(txtCte);
		cteTerceiroRepository.save(cteTerceiro);
		
		List<String> registros = new ArrayList<String>(Arrays.asList(txtCte.split("\\n")));
		List<String> itens;
		for (String test : registros) {
			itens = new ArrayList<String>(Arrays.asList(test.split(Pattern.quote("|"))));
			for (String saida : itens) {
				System.out.println(saida);
			}
		}
		
		
//		Será aberto um thread para transformar o txt em xml e comunicar com a Sefaz

		//GeradorXMLTerceiro gerador = new GeradorXMLTerceiro();
		
		//Document xml = gerador.getDocumentXML(txtCte);
		
		return protocolo;
	}

}
