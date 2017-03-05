package br.com.docrotas.docrotasweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.docrotas.docrotasweb.entity.Uf;

@Repository
public interface UfRepository extends JpaRepository<Uf, Long>, JpaSpecificationExecutor<Uf>{
	
	public Uf findById(Long id);
	
	public List<Uf> findBySiglaContaining(String sigla);
	
	public Uf findByCodIBGE(Long codIBGE);
	
	public List<Uf> findByDescricaoContaining(String descricao);
	
	
}
