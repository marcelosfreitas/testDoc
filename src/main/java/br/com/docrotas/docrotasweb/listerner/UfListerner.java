package br.com.docrotas.docrotasweb.listerner;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import br.com.docrotas.docrotasweb.entity.Uf;

public class UfListerner {
	
	@PrePersist
	public void prePersistUfEvent(Uf uf) {
		inseriDtCriacao(uf);
		atualizaDtAlteracao(uf);
	}
	
	@PreUpdate
	public void preUpdateUfEvent(Uf uf){
		atualizaDtAlteracao(uf);
	}
	
	private void atualizaDtAlteracao(Uf uf) {
		uf.setDtAlteracao(new Date());
		
	}

	private void inseriDtCriacao(Uf uf) {
		if (uf.getDtCriacao() == null) {
			uf.setDtCriacao(new Date());
		}
	}

}
