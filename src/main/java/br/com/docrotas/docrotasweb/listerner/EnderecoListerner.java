package br.com.docrotas.docrotasweb.listerner;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import br.com.docrotas.docrotasweb.entity.Endereco;

public class EnderecoListerner {
	@PrePersist
	public void prePersistEnderecoEvent(Endereco endereco) {
		inseriDtCriacao(endereco);
		atualizaDtAlteracao(endereco);
	}
	
	@PreUpdate
	public void preUpdateEnderecoEvent(Endereco endereco){
		atualizaDtAlteracao(endereco);
	}
	
	private void atualizaDtAlteracao(Endereco endereco) {
		endereco.setDtAlteracao(new Date());
		
	}

	private void inseriDtCriacao(Endereco endereco) {
		if (endereco.getDtCriacao() == null) {
			endereco.setDtCriacao(new Date());
		}
	}
}
