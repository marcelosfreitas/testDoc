package br.com.docrotas.docrotasweb.listerner;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import br.com.docrotas.docrotasweb.entity.NFe;

public class NFeListerner {
	@PrePersist
	public void prePersistNFeEvent(NFe nfe) {
		inseriDtCriacao(nfe);
		atualizaDtAlteracao(nfe);
	}
	
	@PreUpdate
	public void preUpdateNFeEvent(NFe nfe){
		atualizaDtAlteracao(nfe);
	}
	
	private void atualizaDtAlteracao(NFe nfe) {
		nfe.setDtAlteracao(new Date());
		
	}

	private void inseriDtCriacao(NFe nfe) {
		if (nfe.getDtCriacao() == null) {
			nfe.setDtCriacao(new Date());
		}
	}
}
