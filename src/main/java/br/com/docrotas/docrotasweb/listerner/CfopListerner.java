package br.com.docrotas.docrotasweb.listerner;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import br.com.docrotas.docrotasweb.entity.Cfop;

public class CfopListerner {
	
	@PrePersist
	public void prePersistCfopEvent(Cfop cfop) {
		inseriDtCriacao(cfop);
		atualizaDtAlteracao(cfop);
	}
	
	@PreUpdate
	public void preUpdateCfopEvent(Cfop cfop) {
		atualizaDtAlteracao(cfop);
	}
	
	private void atualizaDtAlteracao(Cfop cfop) {
		cfop.setDtAlteracao(new Date());
	}
	
	private void inseriDtCriacao(Cfop cfop) {
		if (cfop.getDtCriacao() == null) {
			cfop.setDtCriacao(new Date());
		}
	}
}
