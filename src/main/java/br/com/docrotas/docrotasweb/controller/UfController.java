package br.com.docrotas.docrotasweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.docrotas.docrotasweb.entity.Uf;
import br.com.docrotas.docrotasweb.filter.UfFilter;
import br.com.docrotas.docrotasweb.repository.RepositoryException;
import br.com.docrotas.docrotasweb.repository.UfRepository;
import br.com.docrotas.docrotasweb.service.ServiceException;
import br.com.docrotas.docrotasweb.service.UfService;
import br.com.docrotas.docrotasweb.specifications.UfSpecifications;

@RestController(value="/app/uf")
public class UfController {

	@Autowired
	UfRepository ufRepository;
//	@Autowired
//	UfService ufService;
	
	@GetMapping
	public List<Uf> buscarTodos() throws RepositoryException {
		UfFilter ufFilter = new UfFilter();
		ufFilter.setDescricao("inas");
		
		return ufRepository.findAll(Specifications.where(new UfSpecifications(ufFilter)));
	}
	
//	@GetMapping("/app/uf{id}")
//	public Uf buscarPorId(@PathVariable Long id){
//		return ufRepository.buscarPorId(id);
//	}
//
//	@PostMapping("/app/uf/filter")
//	public List<Uf> buscarFilter(@RequestBody UfFilter filter) throws RepositoryException{
//		return ufRepository.buscarPorFilter(filter);
//	}
	
//	@PostMapping
//	public Uf salvar(@RequestBody Uf uf) throws RepositoryException, ServiceException {
//		return ufService.salvar(uf);
//	}
//	
//	@DeleteMapping("/app/uf{id}")
//	public void excluir(@PathVariable Long id) throws RepositoryException, ServiceException {
//		ufService.excluir(id);
//	}
}
