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

import br.com.docrotas.docrotasweb.entity.Uf;
import br.com.docrotas.docrotasweb.repository.UfRepository;

@RestController
public class UfController {

	@Autowired
	UfRepository ufRepository;

	@GetMapping("/uf")
	public Page<Uf> buscarTodos(@RequestParam(value = "pagina", required = true)int pagina,
								@RequestParam(value = "qtd", required = true)int qtd,
								@RequestParam(value = "sigla", required = false)String sigla,
								@RequestParam(value = "descricao", required = false)String descricao,
								@RequestParam(value = "codibge", required = false)Long codibge){
		Pageable pageable = new PageRequest(pagina, qtd);		

		Page<Uf> pageUfs;
		if(codibge != null){
			pageUfs = ufRepository.findByCodIBGE(codibge, pageable);
		}else if(sigla != null){
			pageUfs = ufRepository.findBySiglaContaining(sigla, pageable);
		}else if(descricao != null){
			pageUfs = ufRepository.findByDescricaoContaining(descricao, pageable);
		}else{
			pageUfs = ufRepository.findAll(pageable);
		}
		
		return pageUfs;
	}
	
	@PostMapping(value = "/uf")
	public Uf salvar(@RequestBody Uf uf) throws Exception {
		return ufRepository.save(uf);
	}
	
	@DeleteMapping("/uf/{codibge}")
	public void excluir(@PathVariable Long id) {
		ufRepository.delete(id);
	}
}
