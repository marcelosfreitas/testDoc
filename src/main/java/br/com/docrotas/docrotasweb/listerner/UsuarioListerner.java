package br.com.docrotas.docrotasweb.listerner;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import br.com.docrotas.docrotasweb.entity.Usuario;

public class UsuarioListerner {

	@PrePersist
	public void prePersistUfEvent(Usuario usuario) {
		inseriDtCriacao(usuario);
		atualizaDtAlteracao(usuario);
	}
	
	@PreUpdate
	public void preUpdateUfEvent(Usuario usuario){
		atualizaDtAlteracao(usuario);
	}
	
	private void atualizaDtAlteracao(Usuario usuario) {
		usuario.setDtAlteracao(new Date());
		
	}

	private void inseriDtCriacao(Usuario usuario) {
		if (usuario.getDtCriacao() == null) {
			usuario.setDtCriacao(new Date());
		}
	}	
	
}
