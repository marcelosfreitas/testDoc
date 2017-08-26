package br.com.docrotas.docrotasweb.listerner;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import br.com.docrotas.docrotasweb.entity.Empresa;

public class EmpresaListerner {

	@PrePersist
	public void prePersistEmpresaEvent(Empresa empresa) {
		inseriDtCriacao(empresa);
		atualizaDtAlteracao(empresa);
	}
	
	@PreUpdate
	public void preUpdateEmpresaEvent(Empresa empresa) {
		atualizaDtAlteracao(empresa);
	}
	
	private void atualizaDtAlteracao(Empresa empresa) {
		empresa.setDtAlteracao(new Date());
	}
	
	private void inseriDtCriacao(Empresa empresa) {
		if (empresa.getDtCriacao() == null) {
			empresa.setDtCriacao(new Date());
		}
	}
}
