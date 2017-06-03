package br.com.docrotas.docrotasweb.entity;

import java.io.Serializable;

public class EnderecoID implements Serializable {

	private static final long serialVersionUID = 1L;

	private TipoEndereco tipoEndereco;

	private Long pessoa;

	public TipoEndereco getTipoEndereco() {
		return tipoEndereco;
	}

	public void setTipoEndereco(TipoEndereco tipoEndereco) {
		this.tipoEndereco = tipoEndereco;
	}

	public Long getPessoa() {
		return pessoa;
	}

	public void setPessoa(Long pessoa) {
		this.pessoa = pessoa;
	}

	@Override
	public String toString() {
		return "EnderecoID [tipoEndereco=" + tipoEndereco + ", pessoa=" + pessoa + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pessoa == null) ? 0 : pessoa.hashCode());
		result = prime * result + ((tipoEndereco == null) ? 0 : tipoEndereco.hashCode());
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
		EnderecoID other = (EnderecoID) obj;
		if (pessoa == null) {
			if (other.pessoa != null)
				return false;
		} else if (!pessoa.equals(other.pessoa))
			return false;
		if (tipoEndereco != other.tipoEndereco)
			return false;
		return true;
	}

}
