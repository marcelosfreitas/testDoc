package br.com.docrotas.docrotasweb.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.docrotas.docrotasweb.entity.Uf;
import br.com.docrotas.docrotasweb.filter.UfFilter;

public class UfSpecifications implements Specification<Uf>{

	private UfFilter ufFilter;
	
	public UfSpecifications(UfFilter ufFilter) {
		this.ufFilter = ufFilter;
	}

	@Override
	public Predicate toPredicate(Root<Uf> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if (ufFilter.getDescricao() != null && !ufFilter.getDescricao().isEmpty()) {
			return criteriaBuilder.like(root.<String> get("descricao"), "%" + ufFilter.getDescricao() + "%");
		}
		return null;
	}
	

}
