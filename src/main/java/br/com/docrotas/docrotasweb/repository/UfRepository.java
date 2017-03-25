package br.com.docrotas.docrotasweb.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.docrotas.docrotasweb.entity.Uf;

@Repository
public interface UfRepository extends JpaRepository<Uf, Long>, JpaSpecificationExecutor<Uf>{
	
	public Page<Uf> findById(Long id, Pageable pageable);
	
	public Page<Uf> findBySiglaContaining(String sigla, Pageable pageable);
	
	public Page<Uf> findByCodIBGE(Long codIBGE, Pageable pageable);
	
	public Page<Uf> findByDescricaoContaining(String descricao, Pageable pageable);
	
	
}
