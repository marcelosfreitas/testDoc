package br.com.docrotas.docrotasweb.listerner;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import br.com.docrotas.docrotasweb.entity.CTe;

public class CTeListerner {
	@PrePersist
	public void prePersistCTeEvent(CTe cte) {
		inseriDtCriacao(cte);
		atualizaDtAlteracao(cte);
	}
	
	@PreUpdate
	public void preUpdateCTeEvent(CTe cte){
		atualizaDtAlteracao(cte);
	}
	
	private void atualizaDtAlteracao(CTe cte){
		cte.setDtAlteracao(new Date());
	}
	
	private void inseriDtCriacao(CTe cte){
		if(cte.getDtCriacao() == null) {
			cte.setDtCriacao(new Date());
		}
 	}
}
