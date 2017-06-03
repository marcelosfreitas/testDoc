package br.com.docrotas.docrotasweb.entity;

import java.io.Serializable;

public class NFeMedidasId implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private TipoMedidas tipoMedidas;
		
	private Long nfe;

	public TipoMedidas getCodMedida() {
		return tipoMedidas;
	}

	public void setCodMedida(TipoMedidas tipoMedidas) {
		this.tipoMedidas = tipoMedidas;
	}

	public Long getNfe() {
		return nfe;
	}

	public void setNfe(Long nfe) {
		this.nfe = nfe;
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
		NFeMedidasId other = (NFeMedidasId) obj;
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
		return "NFeMedidasId [tipoMedidas=" + tipoMedidas + ", nfe=" + nfe + "]";
	}


}