package br.com.docrotas.docrotasweb.listerner;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import br.com.docrotas.docrotasweb.entity.Conta;

public class ContaListerner {
	
	@PrePersist
	public void prePersistContaEvent(Conta conta) {
		inseriDtCriacao(conta);
		atualizaDtAlteracao(conta);
	}
	
	@PreUpdate
	public void preUpdateContaEvent(Conta conta) {
		atualizaDtAlteracao(conta);
	}	

	private void atualizaDtAlteracao(Conta conta) {
		conta.setDtAlteracao(new Date());	
	}	
	
	private void inseriDtCriacao(Conta conta) {
		if (conta.getDtCriacao() == null) {
			conta.setDtCriacao(new Date());
		}
	}

}
