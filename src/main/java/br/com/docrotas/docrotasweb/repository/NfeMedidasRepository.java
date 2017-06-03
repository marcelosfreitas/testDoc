package br.com.docrotas.docrotasweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.docrotas.docrotasweb.entity.NFeMedidas;

@Repository
public interface NfeMedidasRepository extends JpaRepository<NFeMedidas, Long>, JpaSpecificationExecutor<NFeMedidas>{
	
	public Page<NFeMedidas> findByNfepk(NFeMedidas id, Pageable pageable); 
	
	public Page<NFeMedidas> findByNfepkNfeId(Long nfeId, Pageable pageable);

}
