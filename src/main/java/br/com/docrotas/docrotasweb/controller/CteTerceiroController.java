package br.com.docrotas.docrotasweb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.docrotas.docrotasweb.service.cteTerceiro.CteTerceiroService;

@RestController
public class CteTerceiroController {
	
	private CteTerceiroService cteTerceiroservice;

	@PostMapping(value = "/cteTerceiro/recepcionar")
	public String recepcionarCteTerceiro(@RequestBody String txtCte) {
		String protocolo = "";
		
		protocolo = cteTerceiroservice.recepcionar(txtCte);
		
		return protocolo;
	}
	
	@GetMapping(value = "/cteTerceiro/consultar")
	public String consultaSituacaoProtocolo(@RequestParam String protocolo) {
		String situacao = "";

		return situacao;
	}
	
	@GetMapping(value = "/cteTerceiro/xml")
	public String getXml(@RequestParam String protocolo) {
		String xml = "";

		return xml;
	}
	
	@GetMapping(value = "/cteTerceiro/pdf")
	public String getPdf(@RequestParam String protocolo) {
		String pdf = "";

		return pdf;
	}
}
