package br.com.docrotas.docrotasweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.docrotas.docrotasweb.entity.NFe;

@Repository
public interface NFeRepository extends JpaRepository<NFe, Long>, JpaSpecificationExecutor<NFe> {

	public NFe findById(Long id);
	
	public Page<NFe> findById(Long id, Pageable pageable);
	
	public Page<NFe> findByChave(String chave, Pageable pageable);
	
	public Page<NFe> findByNota(Long nota, Pageable pageable);
	
}
