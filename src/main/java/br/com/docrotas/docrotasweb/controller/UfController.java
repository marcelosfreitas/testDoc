package br.com.docrotas.docrotasweb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Uf> buscarTodos(@RequestParam(value = "id", required = false)Long id,
								@RequestParam(value = "sigla", required = false)String sigla,
								@RequestParam(value = "descricao", required = false)String descricao,
								@RequestParam(value = "codibge", required = false)Long codibge){
		
		List<Uf> ufs = new ArrayList<>();
		if(id != null){
			ufs.add(ufRepository.findById(id));
		}else if(sigla != null){
			ufs = ufRepository.findBySiglaContaining(sigla);
		}else if(descricao != null){
			ufs = ufRepository.findByDescricaoContaining(descricao);
		}else if(codibge != null){
			ufs.add(ufRepository.findByCodIBGE(codibge));
		}else{
			ufs = ufRepository.findAll();
		}
		
		return ufs;
	}
	
	@PostMapping(value = "/uf")
	public Uf salvar(@RequestBody Uf uf) {
		return ufRepository.save(uf);
	}
	
	@DeleteMapping("/uf/{id}")
	public void excluir(@PathVariable Long id) {
		ufRepository.delete(id);
	}
}
