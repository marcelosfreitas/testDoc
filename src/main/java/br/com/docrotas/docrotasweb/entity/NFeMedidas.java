package br.com.docrotas.docrotasweb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.docrotas.docrotasweb.listerner.NFeMedidasListerner;

@Entity
@Table(name = "nfe_medidas")
@IdClass(value = NFeMedidasId.class)
@EntityListeners(value = NFeMedidasListerner.class)
public class NFeMedidas implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Enumerated(EnumType.ORDINAL)
	private TipoMedidas tipoMedidas;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "nfe_id")
	private NFe nfe;
	
	@Column(name = "descricao", length = 20, nullable = false)
	private String descricao;
	
	@Column(name = "valor", nullable = false)
	private Double valor;
	
	@Column(name = "dt_criacao")
	private Date dtCriacao;

	@Column(name = "dt_alteracao")
	private Date dtAlteracao;

	public TipoMedidas getTipoMedidas() {
		return tipoMedidas;
	}

	public void setTipoMedidas(TipoMedidas tipoMedidas) {
		this.tipoMedidas = tipoMedidas;
	}

	public NFe getNfe() {
		return nfe;
	}

	public void setNfe(NFe nfe) {
		this.nfe = nfe;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nfe == null) ? 0 : nfe.hashCode());
		result = prime * result + ((tipoMedidas == null) ? 0 : tipoMedidas.hashCode());
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
		if (nfe == null) {
			if (other.nfe != null)
				return false;
		} else if (!nfe.equals(other.nfe))
			return false;
		if (tipoMedidas != other.tipoMedidas)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NFeMedidas [tipoMedidas=" + tipoMedidas + ", nfe=" + nfe + ", descricao=" + descricao + ", valor="
				+ valor + ", dtCriacao=" + dtCriacao + ", dtAlteracao=" + dtAlteracao + "]";
	}
		
}
