package br.com.docrotas.docrotasweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.docrotas.docrotasweb.entity.Endereco;
import br.com.docrotas.docrotasweb.entity.TipoEndereco;
import br.com.docrotas.docrotasweb.repository.EnderecoRepository;
import br.com.docrotas.docrotasweb.repository.PessoaRepository;

@RestController
public class EnderecoController {

	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired 
	private PessoaRepository pessoaRepository;
	
	@GetMapping("/endereco")
	public Page<Endereco> buscar(@RequestParam(value = "pagina", required = true) int pagina,
								     @RequestParam(value = "qtd", required = true) int qtd,
								     @RequestParam(value = "pessoaId", required = true) Long pessoaID,
									 @RequestParam(value = "tipoEndereco", required = false) Integer tipoEndereco) throws Exception{
		Pageable pageable = new PageRequest(pagina, qtd);

		Page<Endereco> pageEnderecos = null;
		
		if (tipoEndereco != null) {
			pageEnderecos = enderecoRepository.findByPessoaIdAndTipoEndereco(pessoaID, TipoEndereco.geTipoEndereco(tipoEndereco), pageable);
		} else {
			pageEnderecos = enderecoRepository.findByPessoaId(pessoaID, pageable);
		}
		
		return pageEnderecos;
	}
	
	@PostMapping(value = "/endereco")
	public Endereco salvar(@RequestBody Endereco endereco) throws Exception {
		endereco.setPessoa(pessoaRepository.findOne(endereco.getPessoa().getId()));
		return enderecoRepository.save(endereco);
	}
	
//	@DeleteMapping("/endereco/{id}")
//	public void excluir(@PathVariable Long id) {
//		empresaRepository.delete(id);
//	}
}
