package br.com.docrotas.docrotasweb.listerner;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import br.com.docrotas.docrotasweb.entity.Cidade;

public class CidadeListerner {

	@PrePersist
	public void prePersistCidadEvent(Cidade cidade) {
		inseriDtCriacao(cidade);
		atualizaDtAlteracao(cidade);
	}
	
	@PreUpdate
	public void preUpdateCidadeEvent(Cidade cidade) {
		atualizaDtAlteracao(cidade);
	}
	
	private void atualizaDtAlteracao(Cidade cidade) {
		cidade.setDtAlteracao(new Date());
	}
	
	private void inseriDtCriacao(Cidade cidade) {
		if (cidade.getDtCriacao() == null) {
			cidade.setDtCriacao(new Date());
		}
	}
}
