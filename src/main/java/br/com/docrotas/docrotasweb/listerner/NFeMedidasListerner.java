package br.com.docrotas.docrotasweb.listerner;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import br.com.docrotas.docrotasweb.entity.NFeMedidas;

public class NFeMedidasListerner {
	@PrePersist
	public void prePersistNFeMedidasEvent(NFeMedidas nfemedidas) {
		inseriDtCriacao(nfemedidas);
		atualizaDtAlteracao(nfemedidas);
	}
	
	@PreUpdate
	public void preUpdateNFeMedidasEvent(NFeMedidas nfemedidas){
		atualizaDtAlteracao(nfemedidas);
	}
	
	private void atualizaDtAlteracao(NFeMedidas nfemedidas) {
		nfemedidas.setDtAlteracao(new Date());
		
	}

	private void inseriDtCriacao(NFeMedidas nfemedidas) {
		if (nfemedidas.getDtCriacao() == null) {
			nfemedidas.setDtCriacao(new Date());
		}
	}
}
