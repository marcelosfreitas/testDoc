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

import br.com.docrotas.docrotasweb.entity.NFe;
import br.com.docrotas.docrotasweb.repository.NFeRepository;

@RestController
public class NFeController {
	
	@Autowired
	private NFeRepository nfeRepository;
	
	@GetMapping("/nfe")
	public Page<NFe> buscarTodos(@RequestParam(value = "pagina", required = true)int pagina,
							   	 @RequestParam(value = "qtd", required = true)int qtd,
								 @RequestParam(value = "id", required = false)Long id,
								 @RequestParam(value = "nota", required = false)Long nota,
								 @RequestParam(value = "chave", required = false)String chave){
		Pageable pageable = new PageRequest(pagina, qtd);
		
		Page<NFe> pageNfes;
		if(id != null){
			pageNfes = nfeRepository.findById(id, pageable);
		}else if(nota != null){
			pageNfes = nfeRepository.findByNota(nota, pageable);
		}else if(chave != null){
			pageNfes = nfeRepository.findByChave(chave, pageable);
		}else{
			pageNfes = nfeRepository.findAll(pageable);
		}
		
		return pageNfes;
	}
	
	@PostMapping(value = "/nfe")
	public NFe salvar(@RequestBody NFe nfe){
		return nfeRepository.save(nfe);
	}
	
	@DeleteMapping("/nfe/{id}")
	public void excluir(@PathVariable Long id){
		nfeRepository.delete(id);
	}
	
}
