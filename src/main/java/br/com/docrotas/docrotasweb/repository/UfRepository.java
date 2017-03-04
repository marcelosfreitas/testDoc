package br.com.docrotas.docrotasweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.docrotas.docrotasweb.entity.Uf;

@Repository
public interface UfRepository extends JpaRepository<Uf, Long>, JpaSpecificationExecutor<Uf>{
	
}
