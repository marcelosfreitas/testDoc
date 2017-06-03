package br.com.docrotas.docrotasweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.docrotas.docrotasweb.entity.CTe;

@Repository
public interface CTeRepository extends JpaRepository<CTe, Long>, JpaSpecificationExecutor<CTe> {
	
	public CTe findById(Long id);
	
	public Page<CTe> findById(Long id, Pageable pageable);
	
	public CTe findByNumero(Long numero);
	
	public Page<CTe> findByNumero(Long numero, Pageable pageable);
	
	public Page<CTe> findBySituacao(Integer situacao, Pageable pageable);
}
