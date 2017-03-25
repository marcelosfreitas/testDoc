package br.com.docrotas.docrotasweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.docrotas.docrotasweb.entity.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>, JpaSpecificationExecutor<Cidade>{
	
	
	public Page<Cidade> findById(Long id, Pageable pageable);
	
	public Page<Cidade> findByCodIBGE(Long codIBGE, Pageable pageable);
	
	public Page<Cidade> findByNomeContaining(String nome, Pageable pageable);
	
	public Page<Cidade> findByUfId(Long uf, Pageable pageable);
	
}
