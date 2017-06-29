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

import br.com.docrotas.docrotasweb.entity.CTe;
import br.com.docrotas.docrotasweb.repository.CTeRepository;
import br.com.docrotas.docrotasweb.service.cte.CTeService;

@RestController
public class CTeController {

	@Autowired
	private CTeRepository cteRepository;
	@Autowired
	private CTeService cteService;
	
	@GetMapping("/cte")
	public Page<CTe> buscarTodos(@RequestParam(value = "pagina", required = true)int pagina,
							   	 @RequestParam(value = "qtd", required = true)int qtd,
								 @RequestParam(value = "id", required = false)Long id,
								 @RequestParam(value = "numero", required = false)Long numero,
								 @RequestParam(value = "situacao", required = false)Integer situacao){
		Pageable pageable = new PageRequest(pagina, qtd);
		Page<CTe> pageCtes;
		if(id != null){
			pageCtes = cteRepository.findById(id, pageable);
		}else if(numero != null){
			pageCtes = cteRepository.findByNumero(numero, pageable); 
		}else if(situacao != null){
			pageCtes = cteRepository.findBySituacao(situacao, pageable);
		}else{
			pageCtes = cteRepository.findAll(pageable);
		}
		
		return pageCtes;
	}
	
	@PostMapping(value = "/cte")
	public CTe salvar(@RequestBody CTe cte){
		return cteService.salvar(cte);
	}
	
	@PostMapping(value = "/cte/buscarAutorizacao/{id}")
	public CTe buscarAutorizacao(@PathVariable Long id) throws Exception{
		return cteService.buscarAutorizacao(id);
	}
	
	@DeleteMapping("/cte/{id}")
	public void excluir(@PathVariable Long id){
		cteRepository.delete(id);
	}
}
