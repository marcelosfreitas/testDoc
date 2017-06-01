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
import br.com.docrotas.docrotasweb.entity.Endereco;
import br.com.docrotas.docrotasweb.entity.EnderecoID;
import br.com.docrotas.docrotasweb.entity.TipoEndereco;
import br.com.docrotas.docrotasweb.repository.EnderecoRepository;
import br.com.docrotas.docrotasweb.repository.PessoaRepository;

@RestController
public class EnderecoController {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@GetMapping("/endereco")
	public Page<Endereco> buscarTodas(@RequestParam(value = "pagina", required = true) int pagina,
								     @RequestParam(value = "qtd", required = true) int qtd,
								     @RequestParam(value = "pessoaID", required = true) Long pessoaID,
									 @RequestParam(value = "tipo", required = false) Long tipo) throws Exception{
		Pageable pageable = new PageRequest(pagina, qtd);
		
		Page<Endereco> pageEnderecos = enderecoRepository.findByEnderecoIdPessoaId(pessoaID, pageable);
		
		return pageEnderecos;
	}
	
	@PostMapping(value = "/endereco")
	public Endereco salvar(@RequestBody Endereco endereco) throws Exception {
		return enderecoRepository.save(endereco);
	}
	
//	@DeleteMapping("/endereco/{id}")
//	public void excluir(@PathVariable Long id) {
//		empresaRepository.delete(id);
//	}
}
