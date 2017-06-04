package br.com.docrotas.docrotasweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.docrotas.docrotasweb.entity.Cfop;

@Repository
public interface CfopRepository extends JpaRepository<Cfop, Long>, JpaSpecificationExecutor<Cfop> {

	public Cfop findByCodigo(Long codigo);
	
	public Page<Cfop> findByCodigo(Long codigo, Pageable pageable);
	
}
