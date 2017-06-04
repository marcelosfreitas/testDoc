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

import br.com.docrotas.docrotasweb.entity.NFeMedidas;
import br.com.docrotas.docrotasweb.entity.TipoMedidas;
import br.com.docrotas.docrotasweb.repository.NfeMedidasRepository;

@RestController
public class NFeMedidasController {
	
	@Autowired
	private NfeMedidasRepository nfemedidasRepository;
	
	@GetMapping("/nfemedidas")
	public Page<NFeMedidas> buscarTodos(@RequestParam(value = "pagina", required = true)int pagina,
									    @RequestParam(value = "qtd", required = true)int qtd,
							   			@RequestParam(value = "idMedida", required = false)Integer idMedida,
							   			@RequestParam(value = "idNFe", required = true)Long idNfe) throws Exception{
		Pageable pageable = new PageRequest(pagina, qtd);
		
		Page<NFeMedidas> pageNFeMedidas;
		
		if(idMedida != null){
			pageNFeMedidas = nfemedidasRepository.findByTipoMedidasAndNfeId(TipoMedidas.getTipoMedidas(idMedida), idNfe, pageable);
		}else{
			pageNFeMedidas = nfemedidasRepository.findByNfeId(idNfe, pageable);
		}
		
		return pageNFeMedidas;
	}
	
	@PostMapping(value = "/nfemedidas")
	public NFeMedidas salvar(@RequestBody NFeMedidas nfemedidas){
		return nfemedidasRepository.save(nfemedidas);
	}
	
	@DeleteMapping(value = "/nfemedidas{idMedida, idNfe}")
	public void excluir(@PathVariable Long idMedida, @PathVariable Long idNfe){
		nfemedidasRepository.delete(nfemedidasRepository.findByTipoMedidasAndNfeId(idMedida, idNfe));
	}
}
