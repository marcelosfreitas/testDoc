package br.com.docrotas.docrotasweb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.docrotas.docrotasweb.listerner.CfopListerner;

@Entity
@Table(name = "cfop")
@EntityListeners(value=CfopListerner.class)
public class Cfop implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long codigo;
	
	@Column(name = "descricao")
	private String descricao;

	@Column(name = "dt_criacao")
	private Date dtCriacao;
	
	@Column(name = "dt_alteracao")
	private Date dtAlteracao;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Cfop other = (Cfop) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cfop [codigo=" + codigo + ", descricao=" + descricao + ", dtCriacao=" + dtCriacao + ", dtAlteracao="
				+ dtAlteracao + "]";
	}
		
}
