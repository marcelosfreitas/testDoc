package br.com.docrotas.docrotasweb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.docrotas.docrotasweb.listerner.EmpresaListerner;

@Entity
@Table(name = "empresa")
@EntityListeners(value=EmpresaListerner.class)
public class Empresa implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "cnpj", length = 14, nullable = false)
	private String cnpj;
	
	@Column(name = "ie", length = 14, nullable = false)
	private String ie;
	
	@Column(name = "razao", length = 40, nullable = false)
	private String razao;
	
	@Column(name = "fantasia", length = 40, nullable = false)
	private String fantasia;
	
	@Column(name = "logradouro", length = 80, nullable = false)
	private String logradouro;
	
	@Column(name = "nro", nullable = false)
	private Integer nro = 0;
	
	@Column(name = "complemento", length = 40, nullable = true)
	private String complemento;
	
	@Column(name = "bairro", length = 40, nullable = true)
	private String bairro;
	
	@Column(name = "cep", length = 8, nullable = false)
	private String cep;
	
	@ManyToOne
	@JoinColumn(name = "cidade_id")
	private Cidade cidade;
	
	@Column(name = "emite_cte", precision = 1)
	private Boolean emiteCte;
	
	@Enumerated(EnumType.ORDINAL)	
	@Column(name = "ambiente_cte")
	private TipoAmbienteEmissao tipoAmbienteCte;
	
	@Column(name = "emite_mdfe", precision = 1)
	private Boolean emiteMdfe;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "ambiente_mdfe")
	private TipoAmbienteEmissao tipoAmbienteMdfe;

	@Column(name = "dt_criacao")
	private Date dtCriacao;

	@Column(name = "dt_alteracao")
	private Date dtAlteracao;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public String getRazao() {
		return razao;
	}

	public void setRazao(String razao) {
		this.razao = razao;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Integer getNro() {
		return nro;
	}

	public void setNro(Integer nro) {
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

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Boolean getEmiteCte() {
		return emiteCte;
	}

	public void setEmiteCte(Boolean emiteCte) {
		this.emiteCte = emiteCte;
	}

	public Boolean getEmiteMdfe() {
		return emiteMdfe;
	}

	public void setEmiteMdfe(Boolean emiteMdfe) {
		this.emiteMdfe = emiteMdfe;
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
		return "Empresa [id=" + id + ", cnpj=" + cnpj + ", ie=" + ie + ", razao=" + razao + ", fantasia=" + fantasia
				+ ", logradouro=" + logradouro + ", nro=" + nro + ", complemento=" + complemento + ", bairro=" + bairro
				+ ", cep=" + cep + ", cidade=" + cidade + ", emiteCte=" + emiteCte + ", tipoAmbienteCte="
				+ tipoAmbienteCte + ", emiteMdfe=" + emiteMdfe + ", tipoAmbienteMdfe=" + tipoAmbienteMdfe
				+ ", dtCriacao=" + dtCriacao + ", dtAlteracao=" + dtAlteracao + "]";
	}

	public TipoAmbienteEmissao getTipoAmbienteCte() {
		return tipoAmbienteCte;
	}

	public void setTipoAmbienteCte(TipoAmbienteEmissao tipoAmbienteCte) {
		this.tipoAmbienteCte = tipoAmbienteCte;
	}

	public TipoAmbienteEmissao getTipoAmbienteMdfe() {
		return tipoAmbienteMdfe;
	}

	public void setTipoAmbienteMdfe(TipoAmbienteEmissao tipoAmbienteMdfe) {
		this.tipoAmbienteMdfe = tipoAmbienteMdfe;
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
		Empresa other = (Empresa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
