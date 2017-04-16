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

import br.com.docrotas.docrotasweb.entity.Empresa;
import br.com.docrotas.docrotasweb.repository.EmpresaRepository;

@RestController
public class EmpresaController {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@GetMapping("/empresa")
	public Page<Empresa> buscarTodas(@RequestParam(value = "pagina", required = true)int pagina,
								     @RequestParam(value = "qtd", required = true)int qtd,
									 @RequestParam(value = "id", required = false)Long id,
									 @RequestParam(value = "razao", required = false)String razao,
									 @RequestParam(value = "fantasia", required = false)String fantasia,
									 @RequestParam(value = "cidadeId", required = false)Long cidadeId,
									 @RequestParam(value = "cnpj", required = false)String cnpj) throws Exception{
		Pageable pageable = new PageRequest(pagina, qtd);
		
		Page<Empresa> pageEmpresas;
		if(id != null){
			pageEmpresas = empresaRepository.findById(id, pageable);
		}else if(razao != null){
			pageEmpresas = empresaRepository.findByRazaoContaining(razao, pageable);
		}else if(fantasia != null){
			pageEmpresas = empresaRepository.findByFantasiaContaining(fantasia, pageable);			
		}else if(cidadeId != null){
			pageEmpresas = empresaRepository.findByCidadeId(cidadeId, pageable);
		}else if(cnpj != null){
			pageEmpresas = empresaRepository.findByCnpj(cnpj, pageable);
		}else{
			pageEmpresas = empresaRepository.findAll(pageable);
		}
		
		return pageEmpresas;
	}
	
	@PostMapping(value = "/empresa")
	public Empresa salvar(@RequestBody Empresa empresa) {
		return empresaRepository.save(empresa);
	}
	
	@DeleteMapping("/empresa/{id}")
	public void excluir(@PathVariable Long id) {
		empresaRepository.delete(id);
	}	
	
}
