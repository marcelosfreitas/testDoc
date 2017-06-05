package br.com.docrotas.docrotasweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.docrotas.docrotasweb.entity.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>, JpaSpecificationExecutor<Pessoa> {

	public Page<Pessoa> findById(Long id, Pageable pageable);
	
	public Page<Pessoa> findByRazao(String razao, Pageable pageable);
	
	public Page<Pessoa> findByFantasia(String fantasia, Pageable pageable);
	
//	public Page<Pessoa> findByEnderecoCidadeId(Long id, Pageable pageable);
//	
//	public Page<Pessoa> findByEnderecoCidadeNomeContaining(String nome, Pageable pageable);
	
	public Page<Pessoa> findByMotoristaTrue(boolean motorista, Pageable pageable);
	
	public Page<Pessoa> findByClienteTrue(boolean cliente, Pageable pageable);
	
	public Page<Pessoa> findByCpfCnpj(String cpfCnpj, Pageable pageable);
	
}
