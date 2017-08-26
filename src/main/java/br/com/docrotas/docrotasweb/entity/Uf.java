package br.com.docrotas.docrotasweb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.docrotas.docrotasweb.listerner.UfListerner;

@Entity
@Table(name = "uf")
@EntityListeners(value=UfListerner.class)
public class Uf implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_ibge", precision = 2)
	private Long codIBGE;	

	@Column(name = "descricao", length = 45, nullable = false)
	private String descricao;
	
	@Column(name = "sigla", length = 2, nullable = false)
	private String sigla;

	@Column(name = "dt_criacao")
	private Date dtCriacao;

	@Column(name = "dt_alteracao")
	private Date dtAlteracao;
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Long getCodIBGE() {
		return codIBGE;
	}

	public void setCodIBGE(Long codIBGE) {
		this.codIBGE = codIBGE;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codIBGE == null) ? 0 : codIBGE.hashCode());
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
		Uf other = (Uf) obj;
		if (codIBGE == null) {
			if (other.codIBGE != null)
				return false;
		} else if (!codIBGE.equals(other.codIBGE))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Uf [codIBGE=" + codIBGE + ", descricao=" + descricao + ", sigla=" + sigla + ", dtCriacao=" + dtCriacao
				+ ", dtAlteracao=" + dtAlteracao + "]";
	}

}
