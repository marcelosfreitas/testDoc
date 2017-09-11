package br.com.docrotas.docrotasweb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cte_terceiro")
public class CteTerceiro implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", columnDefinition = "long", nullable = false)
	private Long id;
	
	@Column(name = "txterceiro" ,columnDefinition = "text", nullable = false)
	private String txTerceiro;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTxTerceiro() {
		return txTerceiro;
	}

	public void setTxTerceiro(String txTerceiro) {
		this.txTerceiro = txTerceiro;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		CteTerceiro other = (CteTerceiro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CteTerceiro [id=" + id + ", txTerceiro=" + txTerceiro + "]";
	}
	
}
