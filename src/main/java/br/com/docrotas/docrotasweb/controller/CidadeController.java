package br.com.docrotas.docrotasweb.controller;

import java.util.ArrayList;
import java.util.List;

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

import br.com.docrotas.docrotasweb.entity.Cidade;
import br.com.docrotas.docrotasweb.repository.CidadeRepository;

@RestController
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@GetMapping("/cidade")
	public List<Cidade> buscarTodos(@RequestParam(value = "id", required = false)Long id,
									@RequestParam(value = "nome", required = false)String nome,
									@RequestParam(value = "ufId", required = false)Long ufId,
									@RequestParam(value = "codibge", required = false)Long codibge) throws Exception{
		
		List<Cidade> cidades = new ArrayList<>();
		if(id != null){
			cidades.add(cidadeRepository.findById(id));
			
			for (Cidade cidade : cidades) {
				cidade.getUf();
			}
		}else if(nome != null){
			cidades = cidadeRepository.findByNomeContaining(nome);
		}else if(ufId != null){
			cidades = cidadeRepository.findByUf(ufId);
		}else if(codibge != null){
			cidades.add(cidadeRepository.findByCodIBGE(codibge));
		}else{
			cidades = cidadeRepository.findAll();
		}
		
		return cidades;
	}
	
	@PostMapping(value = "/cidade")
	public Cidade salvar(@RequestBody Cidade uf) {
		return cidadeRepository.save(uf);
	}
	
	@DeleteMapping("/cidade/{id}")
	public void excluir(@PathVariable Long id) {
		cidadeRepository.delete(id);
	}	
	
	@GetMapping("/cidade/paginada")
	public List<Cidade> buscaPaginada(@RequestParam(value = "pagina", required = true)int pagina,
			@RequestParam(value = "qtd", required = true)int qtd){
		Pageable pageable = new PageRequest(pagina, qtd);
		
		Page<Cidade> pageCidades = cidadeRepository.findAll(pageable);
		
		System.out.println("TOTAL DE PAGINAS: " + pageCidades.getTotalPages());
		System.out.println("TOTAL DE ELEMENTOS: " + pageCidades.getTotalElements());

		return cidadeRepository.findAll(pageable).getContent();
	}
	
	@GetMapping("/cidade/paginada/nome")
	public Page<Cidade> buscaPaginada(	@RequestParam(value = "pagina", required = true) int pagina,
										@RequestParam(value = "qtd", required = true) int qtd,
										@RequestParam(value = "nome", required = true) String nome){
		Pageable pageable = new PageRequest(pagina, qtd);
		
		Page<Cidade> pageCidades = cidadeRepository.findByNomeContaining(nome, pageable);
		
		System.out.println("TOTAL DE PAGINAS: " + pageCidades.getTotalPages());
		System.out.println("TOTAL DE ELEMENTOS: " + pageCidades.getTotalElements());
		
		List<Cidade> cidades = pageCidades.getContent();

		return pageCidades;
	}
	
}

