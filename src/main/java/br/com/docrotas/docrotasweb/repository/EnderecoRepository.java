package br.com.docrotas.docrotasweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.docrotas.docrotasweb.entity.Endereco;
import br.com.docrotas.docrotasweb.entity.EnderecoID;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, EnderecoID>, JpaSpecificationExecutor<Endereco> {
	
	public Page<Endereco> findByEnderecoIdPessoaId(Long id, Pageable pageable);
	
}
