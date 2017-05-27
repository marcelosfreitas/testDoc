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

import br.com.docrotas.docrotasweb.entity.Veiculo;
import br.com.docrotas.docrotasweb.repository.VeiculoRepository;

@RestController
public class VeiculoController {
	
	@Autowired
	VeiculoRepository veiculoRepository;
	
	@GetMapping("/veiculo")
	public Page<Veiculo> buscarTodos(@RequestParam(value = "pagina", required = true)int pagina,
									 @RequestParam(value = "qtd", required = true)int qtd,
									 @RequestParam(value = "id", required = false)Long id,
									 @RequestParam(value = "placa", required = false)String placa){
		Pageable pageable = new PageRequest(pagina, qtd);
		
		Page<Veiculo> pageVeiculos;
		if(id != null){
			pageVeiculos = veiculoRepository.findById(id, pageable);
		}else if(placa != null){
			pageVeiculos = veiculoRepository.findByPlaca(placa, pageable);
		}else{
			pageVeiculos = veiculoRepository.findAll(pageable);
		}
		
		return pageVeiculos;
	}
	
	@PostMapping(value = "/veiculo")
	public Veiculo salvar(@RequestBody Veiculo veiculo) {
		return veiculoRepository.save(veiculo);
	}
	
	@DeleteMapping("/veiculo/{id}")
	public void excluir(@PathVariable Long id) {
		veiculoRepository.delete(id);
	}
}
