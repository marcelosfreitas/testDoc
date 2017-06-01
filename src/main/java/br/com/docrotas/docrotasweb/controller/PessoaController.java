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

import br.com.docrotas.docrotasweb.entity.Pessoa;
import br.com.docrotas.docrotasweb.repository.PessoaRepository;

@RestController
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@GetMapping("/pessoa")
	public Page<Pessoa> buscarTodos(@RequestParam(value = "pagina", required = true)int pagina,
									@RequestParam(value = "qtd", required = true)int qtd,
									@RequestParam(value = "id", required = false)Long id,
									@RequestParam(value = "cpfCnpj", required = false)String cpfCnpj,
									@RequestParam(value = "razao", required = false)String razao,
									@RequestParam(value = "fantasia", required = false)String fantasia,
									@RequestParam(value = "motorista", required = false)boolean motorista,
									@RequestParam(value = "cliente", required = false)boolean cliente,
									@RequestParam(value = "cidadeId", required = false)Long cidadeId,
									@RequestParam(value = "cidadeNome", required = false)String cidadeNome){
		Pageable pageable = new PageRequest(pagina, qtd);
		
		Page<Pessoa> pagePessoas = null;
		if(id != null){
			pagePessoas = pessoaRepository.findById(id, pageable);
		}else if(cpfCnpj != null){
			pagePessoas = pessoaRepository.findByCpfCnpj(cpfCnpj, pageable);
		}else if(razao != null){
			pagePessoas = pessoaRepository.findByRazao(razao, pageable);
		}else if(fantasia != null){
			pagePessoas = pessoaRepository.findByFantasia(fantasia, pageable);
		}else if(motorista == true){
			pagePessoas = pessoaRepository.findByMotoristaTrue(motorista, pageable);
		}else if(cliente == true){
			pagePessoas = pessoaRepository.findByClienteTrue(cliente, pageable);
		}else if(cidadeId != null){
			//pagePessoas = pessoaRepository.findByEnderecoCidadeId(cidadeId, pageable);
		}else if(cidadeNome != null){
			//pagePessoas = pessoaRepository.findByEnderecoCidadeNomeContaining(cidadeNome, pageable);
		}else{
			pagePessoas = pessoaRepository.findAll(pageable);
		}

		return pagePessoas;
	}
	
	@PostMapping(value = "/pessoa")
	public Pessoa salvar(@RequestBody Pessoa pessoa){
		return pessoaRepository.save(pessoa);
	}
	
	@DeleteMapping("/pessoa/{id}")
	public void excluir(@PathVariable Long id){
		pessoaRepository.delete(id);
	}
}
