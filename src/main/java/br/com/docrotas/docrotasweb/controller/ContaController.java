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

import br.com.docrotas.docrotasweb.entity.Conta;
import br.com.docrotas.docrotasweb.repository.ContaRepository;

@RestController
public class ContaController {

	@Autowired
	private ContaRepository contaRepository;
	
	@GetMapping("/conta")
	public Page<Conta> buscarTodos(@RequestParam(value = "pagina", required = true)int pagina,
								   @RequestParam(value = "qtd", required = true)int qtd,
								   @RequestParam(value = "id", required = false)Long id) throws  Exception{
		
		Pageable pageable = new PageRequest(pagina, qtd);
		
		Page<Conta> pageContas;
		if(id != null) {
			pageContas = contaRepository.findById(id, pageable);
		}else {
			pageContas = contaRepository.findAll(pageable);
		}
		
		return pageContas;
	}
	
	@PostMapping(value = "/conta")
	public Conta salvar(@RequestBody Conta conta) {
		return contaRepository.save(conta);
	}
	
	@DeleteMapping("/conta/{id}")
	public void excluir(@PathVariable Long id) {
		contaRepository.delete(id);
	}
}
