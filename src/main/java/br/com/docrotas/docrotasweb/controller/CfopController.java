package br.com.docrotas.docrotasweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.docrotas.docrotasweb.entity.Cfop;
import br.com.docrotas.docrotasweb.repository.CfopRepository;

@RestController
public class CfopController {

	@Autowired
	private CfopRepository cfopRepository;
	
	@GetMapping("/cfop")
	public Page<Cfop> buscarTodos(@RequestParam(value = "pagina", required = true)int pagina,
								  @RequestParam(value = "qtd", required = true)int qtd,
								  @RequestParam(value = "codigo", required = false)Long codigo){
		Pageable pageable = new PageRequest(pagina, qtd);
		
		Page<Cfop> pageCfops;
		if(codigo != null){
			pageCfops = cfopRepository.findByCodigo(codigo, pageable);
		}else{
			pageCfops = cfopRepository.findAll(pageable);
		}
		return pageCfops;
	}
	
	@PostMapping(value = "/cfop")
	public Cfop salvar(@RequestBody Cfop cfop){
		return cfopRepository.save(cfop);
	}
	
	@DeleteMapping("/cfop/{codigo}")
	public void excluir(@PathVariable Long codigo){
		cfopRepository.delete(codigo);
	}
}
