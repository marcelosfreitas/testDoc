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
				
		CTeComunicaoService2 cTeComunicaoService = new CTeComunicaoService2();
		
		cTeComunicaoService.recepicionarLote(xmlString);
		
	}
	
	

}
