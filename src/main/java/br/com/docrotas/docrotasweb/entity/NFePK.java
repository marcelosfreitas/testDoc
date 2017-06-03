package br.com.docrotas.docrotasweb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class NFePK implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(name = "cod_medida", nullable = false)
	private Long codMedida;
	
	@ManyToOne
	@JoinColumn(name = "nfe_id")	
	private NFe nfe;

	public Long getCodMedida() {
		return codMedida;
	}

	public void setCodMedida(Long codMedida) {
		this.codMedida = codMedida;
	}

	public NFe getNfe() {
		return nfe;
	}

	public void setNfe(NFe nfe) {
		this.nfe = nfe;
	}

	@Override
	public String toString() {
		return "NFePK [codMedida=" + codMedida + ", nfe=" + nfe + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codMedida == null) ? 0 : codMedida.hashCode());
		result = prime * result + ((nfe == null) ? 0 : nfe.hashCode());
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
		NFePK other = (NFePK) obj;
		if (codMedida == null) {
			if (other.codMedida != null)
				return false;
		} else if (!codMedida.equals(other.codMedida))
			return false;
		if (nfe == null) {
			if (other.nfe != null)
				return false;
		} else if (!nfe.equals(other.nfe))
			return false;
		return true;
	}

}