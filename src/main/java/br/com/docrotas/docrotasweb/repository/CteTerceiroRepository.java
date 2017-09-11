package br.com.docrotas.docrotasweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.docrotas.docrotasweb.entity.CteTerceiro;

@Repository
public interface CteTerceiroRepository extends JpaRepository<CteTerceiro, Long>, JpaSpecificationExecutor<CteTerceiro> {
	
	public Page<CteTerceiro> findById(Long id, Pageable pageable);
	
}
