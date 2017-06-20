package br.com.docrotas.docrotasweb.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.docrotas.docrotasweb.listerner.NFeListerner;

@Entity
@Table(name = "nfe")
@EntityListeners(value=NFeListerner.class)
public class NFe implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "serie")
	private Integer serie;
	
	@Column(name = "nota")
	private Long nota;
	
	@Column(name = "valor_nf")
	private Double valorNf;
	
	@Column(name = "valor_produto")
	private Double valorProd;
	
	@Column(name = "chave", length = 44, nullable = true)
	private String chave;
	
	@Column(name = "dt_criacao")
	private Date dtCriacao;

	@Column(name = "dt_alteracao")
	private Date dtAlteracao;
	
	@OneToMany(mappedBy="nfe", cascade=CascadeType.ALL)
	private List<NFeMedidas> nfeMedidas;
	
	public Map<TipoMedidas, Double> totalMedidas(){
		Map<TipoMedidas, Double> totalMedidas = new HashMap<TipoMedidas, Double>();
		
		for (NFeMedidas nfeMedidas : getNfeMedidas()) {
			Double valor = totalMedidas.get(nfeMedidas.getTipoMedidas());

			if (valor == null) {
				valor = Double.valueOf(0);
			}

			valor = valor + nfeMedidas.getValor();
			totalMedidas.put(nfeMedidas.getTipoMedidas(), valor);
			
		}
		
		return totalMedidas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSerie() {
		return serie;
	}

	public void setSerie(Integer serie) {
		this.serie = serie;
	}

	public Long getNota() {
		return nota;
	}

	public void setNota(Long nota) {
		this.nota = nota;
	}

	public Double getValorNf() {
		return valorNf;
	}

	public void setValorNf(Double valorNf) {
		this.valorNf = valorNf;
	}

	public Double getValorProd() {
		return valorProd;
	}

	public void setValorProd(Double valorProd) {
		this.valorProd = valorProd;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
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
	
	public List<NFeMedidas> getNfeMedidas() {
		return nfeMedidas;
	}

	public void setNfeMedidas(List<NFeMedidas> nfeMedidas) {
		this.nfeMedidas = nfeMedidas;
	}

	@Override
	public String toString() {
		return "NFe [id=" + id + ", serie=" + serie + ", nota=" + nota + ", valorNf=" + valorNf + ", valorProd="
				+ valorProd + ", chave=" + chave + ", dtCriacao=" + dtCriacao + ", dtAlteracao=" + dtAlteracao + "]";
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
		NFe other = (NFe) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
		
}
