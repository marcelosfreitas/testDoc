package br.com.docrotas.docrotasweb.service.cte;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.docrotas.docrotasweb.entity.CTe;
import br.com.docrotas.docrotasweb.repository.CTeRepository;

public class CTeService {
	
	@Autowired
	private CTeRepository cteRepository;
	
	public void buscarAutorizacao(Long id) {
		CTe cte = cteRepository.findById(id);
		
		GeradorXmlCte geradorXmlCte = new GeradorXmlCte();
		
	}

}
