package br.com.docrotas.docrotasweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.docrotas.docrotasweb.entity.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>, JpaSpecificationExecutor<Empresa> {
	
	public Page<Empresa> findById(Long id, Pageable pageable);
	
	public Page<Empresa> findByRazaoContaining(String razao, Pageable pageable);
	
	public Page<Empresa> findByFantasiaContaining(String fantasia, Pageable pageable);
	
	public Page<Empresa> findByCnpj(String cnpj, Pageable pageable);

	public Page<Empresa> findByCidadeId(Long cidadeId, Pageable pageable);
	
}
