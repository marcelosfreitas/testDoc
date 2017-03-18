package br.com.docrotas.docrotasweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.docrotas.docrotasweb.entity.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>, JpaSpecificationExecutor<Cidade>{
	
	public Cidade findById(Long id);
	
	public Cidade findByCodIBGE(Long codIBGE);
	
	public List<Cidade> findByNomeContaining(String nome);
	
	@Query(value = "SELECT * FROM CIDADE c, UF u WHERE c.uf_id = u.id and u.id = ?1", nativeQuery = true)
	public List<Cidade> findByUf(Long uf);
	
}
