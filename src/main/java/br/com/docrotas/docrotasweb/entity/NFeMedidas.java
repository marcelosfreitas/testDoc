package br.com.docrotas.docrotasweb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import br.com.docrotas.docrotasweb.listerner.NFeMedidasListerner;

@Entity
@Table(name = "nfe_medidas")
@EntityListeners(value = NFeMedidasListerner.class)
public class NFeMedidas implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private NFePK nfepk;
	
	@Column(name = "descricao", length = 20, nullable = false)
	private String descricao;
	
	@Column(name = "valor", nullable = false)
	private Double valor;
	
	@Column(name = "dt_criacao")
	private Date dtCriacao;

	@Column(name = "dt_alteracao")
	private Date dtAlteracao;

	public NFePK getNfepk() {
		return nfepk;
	}

	public void setNfepk(NFePK nfepk) {
		this.nfepk = nfepk;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public Date getDtAlteracao() {
		return dtAlteracao;
	}

	public void setDtAlteracao(Date dtAlteracao) {
		this.dtAlteracao = dtAlteracao;
	}

	@Override
	public String toString() {
		return "NFeMedidas [nfepk=" + nfepk + ", descricao=" + descricao + ", valor=" + valor + ", dtCriacao="
				+ dtCriacao + ", dtAlteracao=" + dtAlteracao + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nfepk == null) ? 0 : nfepk.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NFeMedidas other = (NFeMedidas) obj;
		if (nfepk == null) {
			if (other.nfepk != null)
				return false;
		} else if (!nfepk.equals(other.nfepk))
			return false;
		return true;
	}

		
}
