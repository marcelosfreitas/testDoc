package br.com.docrotas.docrotasweb.listerner;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import br.com.docrotas.docrotasweb.entity.Veiculo;

public class VeiculoListerner {
	
	@PrePersist
	public void prePersistVeiculoEvent(Veiculo veiculo) {
		inseriDtCriacao(veiculo);
		atualizaDtAlteracao(veiculo);
	}
	
	@PreUpdate
	public void preUpdateVeiculoEvent(Veiculo veiculo){
		atualizaDtAlteracao(veiculo);
	}

	private void atualizaDtAlteracao(Veiculo veiculo) {
		veiculo.setDtAlteracao(new Date());
	}
	
	private void inseriDtCriacao(Veiculo veiculo) {
		if (veiculo.getDtCriacao() == null) {
			veiculo.setDtCriacao(new Date());
		}
	}
}
