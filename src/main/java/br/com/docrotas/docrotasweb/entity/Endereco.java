package br.com.docrotas.docrotasweb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.docrotas.docrotasweb.listerner.EnderecoListerner;

@Entity
@Table(name = "endereco")
@EntityListeners(value = EnderecoListerner.class)
public class Endereco implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EnderecoID enderecoId;

	@Column(name = "logradouro", length = 60, nullable = false)
	private String logradouro;

	@Column(name = "nro")
	private Long nro;

	@Column(name = "complemento", length = 60, nullable = false)
	private String complemento;

	@Column(name = "bairro", length = 40, nullable = false)
	private String bairro;

	@Column(name = "cep", length = 8, nullable = false)
	private String cep;

	@Column(name = "dt_criacao")
	private Date dtCriacao;

	@Column(name = "dt_alteracao")
	private Date dtAlteracao;

	@ManyToOne
	@JoinColumn(name = "cidade_id")
	private Cidade cidade;

	public EnderecoID getEnderecoId() {
		return enderecoId;
	}

	public void setEnderecoId(EnderecoID enderecoId) {
		this.enderecoId = enderecoId;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Long getNro() {
		return nro;
	}

	public void setNro(Long nro) {
		this.nro = nro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
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

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((enderecoId == null) ? 0 : enderecoId.hashCode());
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
		Endereco other = (Endereco) obj;
		if (enderecoId == null) {
			if (other.enderecoId != null)
				return false;
		} else if (!enderecoId.equals(other.enderecoId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Endereco [enderecoID=" + enderecoId + ", logradouro=" + logradouro + ", nro=" + nro + ", complemento="
				+ complemento + ", bairro=" + bairro + ", cep=" + cep + ", dtCriacao=" + dtCriacao + ", dtAlteracao="
				+ dtAlteracao + ", cidade=" + cidade + "]";
	}

}
