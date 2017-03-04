package br.com.docrotas.docrotasweb.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.com.docrotas.docrotasweb.entity.Uf;
import br.com.docrotas.docrotasweb.filter.UfFilter;

public class UfSpecificationsBuilder {
	
	private UfFilter ufFilter;

	public UfSpecificationsBuilder(UfFilter ufFilter) {
		this.ufFilter = ufFilter;
	}
	
	public Specification<Uf> build() {
		if (ufFilter == null) {
			return null;
		}
		
		List<Specification<Uf>> specs = new ArrayList<Specification<Uf>>();
		
		if (ufFilter.getDescricao() == null && !ufFilter.getDescricao().isEmpty()) {
			
		}
		
		return null;
	}

	public UfFilter getUfFilter() {
		return ufFilter;
	}

	public void setUfFilter(UfFilter ufFilter) {
		this.ufFilter = ufFilter;
	}
	
	

}
